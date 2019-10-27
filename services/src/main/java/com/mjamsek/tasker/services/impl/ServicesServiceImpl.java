package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryFilter;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.enums.FilterOperation;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.dto.ServiceToken;
import com.mjamsek.tasker.entities.persistence.service.*;
import com.mjamsek.tasker.lib.v1.ApiService;
import com.mjamsek.tasker.lib.v1.ClientApp;
import com.mjamsek.tasker.lib.v1.Service;
import com.mjamsek.tasker.lib.v1.WebApp;
import com.mjamsek.tasker.lib.v1.enums.LogSeverity;
import com.mjamsek.tasker.lib.v1.enums.ServiceType;
import com.mjamsek.tasker.lib.v1.exceptions.*;
import com.mjamsek.tasker.lib.v1.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.lib.v1.exceptions.PersistenceException;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerContainerInfo;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerState;
import com.mjamsek.tasker.mappers.DockerMapper;
import com.mjamsek.tasker.mappers.ServiceMapper;
import com.mjamsek.tasker.providers.AuthContext;
import com.mjamsek.tasker.services.DockerService;
import com.mjamsek.tasker.services.LogService;
import com.mjamsek.tasker.services.ServicesService;
import com.mjamsek.tasker.services.Validator;
import com.mjamsek.tasker.utils.HttpClient;
import com.mjamsek.tasker.utils.RandomStringGenerator;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServicesServiceImpl implements ServicesService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private DockerService dockerService;
    
    @Inject
    private LogService logService;
    
    @Inject
    private Validator validator;
    
    @Inject
    private AuthContext authContext;
    
    @Override
    public List<Service> getServices(QueryParameters queryParameters) {
        queryParameters.getFilters().add(new QueryFilter("active", FilterOperation.EQ, "true"));
        return JPAUtils
            .queryEntities(em, ServiceEntity.class, queryParameters)
            .stream()
            .map(ServiceMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public long getServicesCount(QueryParameters queryParameters) {
        queryParameters.getFilters().add(new QueryFilter("active", FilterOperation.EQ, "true"));
        return JPAUtils.queryEntitiesCount(em, ServiceEntity.class, queryParameters);
    }
    
    @Override
    public Service getService(String serviceId) {
        ServiceEntity entity = this.getServiceById(serviceId);
        return null;
    }
    
    @Override
    public ServiceEntity getServiceByName(String name) {
        TypedQuery<ServiceEntity> query = em.createNamedQuery(ServiceEntity.FIND_BY_NAME, ServiceEntity.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    @Override
    public ServiceEntity getServiceById(String serviceId) {
        return em.find(ServiceEntity.class, serviceId);
    }
    
    @Override
    public Service getServiceByIdOrName(String idOrName) {
        ServiceEntity entity;
        Pattern pattern = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        Matcher matcher = pattern.matcher(idOrName);
        if (matcher.find()) {
            entity = this.getServiceById(idOrName);
        } else {
            entity = this.getServiceByName(idOrName);
        }
        return ServiceMapper.fromEntity(entity);
    }
    
    @Override
    public Service createService(Service service) {
        
        if (service == null) {
            throw new ValidationException("validation.empty.payload");
        }
        
        validator.assertNotBlank(service.getName(), "name");
        validator.assertNotBlank(service.getVersion(), "version");
        validator.assertNotNull(service.getType(), "type");
        
        ServiceEntity existing = this.getExisting(service.getName(), service.getVersion());
        if (existing != null) {
            throw new ConflictException("Service with given name and version already exists!");
        }
        
        ServiceEntity entity = ServiceMapper.toEntity(service);
        
        entity.setActive(true);
        
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Service '" + service.getName() + "' was created.");
            return ServiceMapper.fromEntity(entity);
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            logService.log(LogSeverity.ERROR, "Error saving service '" + service.getName() + "'!");
            throw new TaskerException("Error saving entity!");
        }
    }
    
    @Override
    public Service updateService(Service service, String serviceId) {
        ServiceEntity entity = getServiceById(serviceId);
        if (entity == null) {
            throw new EntityNotFoundException("Service with given id doesn't exist!");
        }
    
        if (service.getType().equals(ServiceType.CLIENT_APP) && entity instanceof ClientAppServiceEntity) {
            ClientApp clientApp = (ClientApp) service;
            validator.assertNotBlank(clientApp.getApplicationUrl());
            
            ((ClientAppServiceEntity) entity).setApplicationUrl(clientApp.getApplicationUrl());
            
        } else if (service.getType().equals(ServiceType.WEB_APP) && entity instanceof WebAppServiceEntity) {
            WebApp webApp = (WebApp) service;
            validator.assertNotBlank(webApp.getApplicationUrl());
            validator.assertNotBlank(webApp.getBaseUrl());
            validator.assertNotNull(webApp.getMajorVersion());
            
            ((WebAppServiceEntity) entity).setApplicationUrl(webApp.getApplicationUrl());
            ((WebAppServiceEntity) entity).setMajorVersion(webApp.getMajorVersion());
            ((WebAppServiceEntity) entity).setBaseUrl(webApp.getBaseUrl());
            ((WebAppServiceEntity) entity).setHealthcheckUrl(webApp.getHealthcheckUrl());
            
        } else if (service.getType().equals(ServiceType.API_SERVICE) && entity instanceof ApiServiceEntity) {
            ApiService apiService = (ApiService) service;
            validator.assertNotBlank(apiService.getBaseUrl());
            validator.assertNotNull(apiService.getMajorVersion());
            
            ((ApiServiceEntity) entity).setMajorVersion(apiService.getMajorVersion());
            ((ApiServiceEntity) entity).setBaseUrl(apiService.getBaseUrl());
            ((ApiServiceEntity) entity).setHealthcheckUrl(apiService.getHealthcheckUrl());
        } else {
            throw new ValidationException("validation.error.type.mismatch")
                .withField("type")
                .withEntity("ServiceType");
        }
        
        if (service.getDeployment() != null) {
            ServiceDeploymentEntity deploymentEntity = ServiceMapper.deploymentToEntity(service.getDeployment());
            entity.setDeployment(deploymentEntity);
        } else {
            entity.setDeployment(null);
        }
    
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Service '" + service.getName() + "' was updated.");
            return service;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logService.log(LogSeverity.ERROR, "Error saving service '" + service.getName() + "'!");
            throw new PersistenceException();
        }
    }
    
    @Override
    public ServiceToken generateServiceToken(String serviceId) {
        ServiceEntity entity = getServiceById(serviceId);
        if (entity == null) {
            throw new ServiceNotFoundException(serviceId);
        }
    
        ServiceToken tokenResponse = new ServiceToken();
        ServiceTokenEntity existingToken = this.getExistingServiceToken(serviceId, authContext.getId());
        if (existingToken != null) {
            tokenResponse.setToken(RandomStringGenerator.generate(20));
            existingToken.setToken(BCrypt.hashpw(tokenResponse.getToken(), BCrypt.gensalt()));
            try {
                em.getTransaction().begin();
                em.merge(existingToken);
                em.getTransaction().commit();
                return tokenResponse;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new PersistenceException();
            }
        } else {
            ServiceTokenEntity token = new ServiceTokenEntity();
            token.setService(entity);
            token.setUserId(authContext.getId());
            tokenResponse.setToken(RandomStringGenerator.generate(20));
            token.setToken(BCrypt.hashpw(tokenResponse.getToken(), BCrypt.gensalt()));
            try {
                em.getTransaction().begin();
                em.persist(token);
                em.getTransaction().commit();
                return tokenResponse;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new PersistenceException();
            }
        }
    }
    
    private ServiceTokenEntity getExistingServiceToken(String serviceId, String userId) {
        TypedQuery<ServiceTokenEntity> query = em.createNamedQuery(ServiceTokenEntity.FIND_BY_USER_AND_SERVICE, ServiceTokenEntity.class);
        query.setParameter("serviceId", serviceId);
        query.setParameter("userId", userId);
        try {
            return query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }
    }
    
    @Override
    public void doHealthCheck(String serviceId) {
        ServiceEntity entity = getServiceById(serviceId);
        if (entity == null) {
            throw new ServiceNotFoundException(serviceId);
        }
        
        String healthcheckUrl;
        if (entity instanceof ApiServiceEntity) {
            healthcheckUrl = ((ApiServiceEntity) entity).getHealthcheckUrl();
        } else if (entity instanceof WebAppServiceEntity) {
            healthcheckUrl = ((WebAppServiceEntity) entity).getHealthcheckUrl();
        } else {
            throw new MissingHealthCheckException();
        }
        
        if (healthcheckUrl == null) {
            throw new MissingHealthCheckException();
        }
        
        Response response = HttpClient.get(healthcheckUrl);
        if (response.getStatus() >= 400) {
            throw new FailedHealthCheckException(entity.getName());
        }
    }
    
    @Override
    public void deleteService(String serviceId) {
        ServiceEntity service = getServiceById(serviceId);
        if (service == null) {
            throw new ServiceNotFoundException(serviceId);
        }
        try {
            em.getTransaction().begin();
            service.setActive(false);
            em.merge(service);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Service '" + service.getName() + "' was deactivated.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            logService.log(LogSeverity.ERROR, "Error deactivating service '" + service.getName() + "'!");
        }
    }
    
    @Override
    public DockerContainerInfo getServiceContainer(String serviceId) {
        ServiceEntity service = getDeployedService(serviceId);
        
        return dockerService.getContainerInfo(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerEndpoint()
        );
    }
    
    @Override
    public String getRawServiceContainer(String serviceId) {
        ServiceEntity service = getDeployedService(serviceId);
        
        return dockerService.getRawContainerInfo(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerEndpoint()
        );
    }
    
    @Override
    public DockerState getContainerState(String serviceId) {
        ServiceEntity service = getDeployedService(serviceId);
        
        DockerContainerInfo containerInfo = dockerService.getContainerInfo(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerEndpoint()
        );
        
        return DockerMapper.fromInfoToState(containerInfo);
    }
    
    @Override
    public void startContainer(String serviceId) {
        ServiceEntity service = getDeployedService(serviceId);
        dockerService.startContainer(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerEndpoint()
        );
        logService.log(LogSeverity.INFO, "Service's container (" + service.getName() + ") was recreated.");
    }
    
    @Override
    public void stopContainer(String serviceId) {
        ServiceEntity service = getDeployedService(serviceId);
        dockerService.stopContainer(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerEndpoint()
        );
    }
    
    @Override
    public void recreateContainer(String serviceId) {
        ServiceEntity service = getDeployedService(serviceId);
        String newContainerId = dockerService.recreateContainer(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerEndpoint()
        );
        
        service.getDeployment().setContainerId(newContainerId);
        
        try {
            em.getTransaction().begin();
            em.merge(service);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Container for service '" + service.getName() + "' was recreated.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            logService.log(LogSeverity.ERROR, "Error recreating container for service '" + service.getName() + "'!");
        }
    }
    
    private ServiceEntity getExisting(String name, String version) {
        TypedQuery<ServiceEntity> query = em.createNamedQuery(ServiceEntity.FIND_BY_NAME_AND_VERSION, ServiceEntity.class);
        query.setParameter("name", name);
        query.setParameter("version", version);
        List<ServiceEntity> entities = query.getResultList();
        return entities.size() > 0 ? entities.get(0) : null;
    }
    
    private ServiceEntity getDeployedService(String serviceId) {
        ServiceEntity service = getServiceById(serviceId);
        if (service == null) {
            throw new EntityNotFoundException("Service not found!");
        }
        
        if (service.getDeployment() == null) {
            throw new NotDeployedException("Service is not deployed on docker.");
        }
        return service;
    }
}
