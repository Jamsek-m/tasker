package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryFilter;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.enums.FilterOperation;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.docker.DockerContainerInfo;
import com.mjamsek.tasker.entities.docker.DockerState;
import com.mjamsek.tasker.entities.dto.ServiceRequest;
import com.mjamsek.tasker.entities.dto.ServiceToken;
import com.mjamsek.tasker.entities.exceptions.*;
import com.mjamsek.tasker.entities.persistence.service.*;
import com.mjamsek.tasker.mappers.DockerMapper;
import com.mjamsek.tasker.services.DockerDaemonService;
import com.mjamsek.tasker.services.DockerService;
import com.mjamsek.tasker.services.ServicesService;
import com.mjamsek.tasker.utils.HttpClient;
import com.mjamsek.tasker.utils.RandomStringGenerator;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class ServicesServiceImpl implements ServicesService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private DockerDaemonService dockerDaemonService;
    
    @Inject
    private DockerService dockerService;
    
    @Override
    public List<Service> getServices(QueryParameters queryParameters) {
        queryParameters.getFilters().add(new QueryFilter("active", FilterOperation.EQ, "true"));
        return JPAUtils.queryEntities(em, Service.class, queryParameters);
    }
    
    @Override
    public long getServicesCount(QueryParameters queryParameters) {
        queryParameters.getFilters().add(new QueryFilter("active", FilterOperation.EQ, "true"));
        return JPAUtils.queryEntitiesCount(em, Service.class, queryParameters);
    }
    
    @Override
    public Service getServiceByName(String name) {
        TypedQuery<Service> query = em.createNamedQuery(Service.FIND_BY_NAME, Service.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    @Override
    public Service getServiceById(long serviceId) {
        return em.find(Service.class, serviceId);
    }
    
    @Override
    public Service getServiceByIdOrName(String idOrName) {
        Service service;
        try {
            long serviceId = Long.parseLong(idOrName);
            service = getServiceById(serviceId);
            if (service == null) {
                throw new ServiceNotFoundException(serviceId);
            }
        } catch (NumberFormatException exc) {
            service = getServiceByName(idOrName);
            if (service == null) {
                throw new ServiceNotFoundException(idOrName);
            }
        }
        return service;
    }
    
    @Override
    public Service createService(ServiceRequest dto) {
        
        Service existing = this.getServiceByName(dto.getName());
        if (existing != null && dto.getVersion().equals(existing.getVersion())) {
            throw new ConflictException("Service with given name and version already exists!");
        }
        
        Service service = new Service();
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setVersion(dto.getVersion());
        service.setActive(true);
        
        if (dto.isDeployed()) {
            ServiceUrl url = new ServiceUrl();
            url.setUrl(dto.getServiceUrl().getUrl());
            url.setUrlVersioning(dto.getServiceUrl().isUrlVersioning());
            url.setVersion(dto.getServiceUrl().getVersion());
            service.setServiceUrl(url);
        }
        
        if (dto.isHasHealthcheck()) {
            ServiceHealthCheck health = new ServiceHealthCheck();
            health.setHealthUrl(dto.getHealthCheck().getHealthUrl());
            service.setHealthCheck(health);
        }
        
        if (dto.isDockerized()) {
            ServiceDeployment deployment = new ServiceDeployment();
            deployment.setContainerName(dto.getDeployment().getContainerName());
            deployment.setContainerId(dto.getDeployment().getContainerId());
            DockerDaemon daemon = dockerDaemonService.getDaemon(dto.getDeployment().getDockerDaemon().getName());
            if (daemon == null) {
                throw new ValidationException("Invalid daemon name!");
            }
            deployment.setDockerDaemon(daemon);
            service.setDeployment(deployment);
        }
        
        try {
            em.getTransaction().begin();
            em.persist(service);
            em.getTransaction().commit();
            return service;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new TaskerException("Error saving entity!");
        }
    }
    
    @Override
    public Service updateService(ServiceRequest dto, long serviceId) {
        Service service = getServiceById(serviceId);
        if (service == null) {
            throw new EntityNotFoundException("Service with given id doesn't exist!");
        }
        
        service.setDescription(dto.getDescription());
        
        if (dto.isDeployed()) {
            if (service.getServiceUrl() != null) {
                service.getServiceUrl().setUrl(dto.getServiceUrl().getUrl());
                service.getServiceUrl().setUrlVersioning(dto.getServiceUrl().isUrlVersioning());
                service.getServiceUrl().setVersion(dto.getServiceUrl().getVersion());
            } else {
                ServiceUrl serviceUrl = new ServiceUrl();
                serviceUrl.setVersion(dto.getServiceUrl().getVersion());
                serviceUrl.setUrl(dto.getServiceUrl().getUrl());
                serviceUrl.setUrlVersioning(dto.getServiceUrl().isUrlVersioning());
                service.setServiceUrl(serviceUrl);
            }
        } else {
            service.setServiceUrl(null);
        }
        
        if (dto.isDockerized()) {
    
            DockerDaemon daemon = dockerDaemonService.getDaemon(dto.getDeployment().getDockerDaemon().getName());
            if (daemon == null) {
                throw new ValidationException("Invalid daemon name!");
            }
            
            if (service.getDeployment() != null) {
                service.getDeployment().setContainerId(dto.getDeployment().getContainerId());
                service.getDeployment().setDockerDaemon(daemon);
                service.getDeployment().setContainerName(dto.getDeployment().getContainerName());
            } else {
                ServiceDeployment serviceDeployment = new ServiceDeployment();
                serviceDeployment.setContainerName(dto.getDeployment().getContainerName());
                serviceDeployment.setContainerId(dto.getDeployment().getContainerId());
                serviceDeployment.setDockerDaemon(daemon);
                service.setDeployment(serviceDeployment);
            }
        } else {
            service.setDeployment(null);
        }
        
        if (dto.isHasHealthcheck()) {
            if (service.getHealthCheck() != null) {
                service.getHealthCheck().setHealthUrl(dto.getHealthCheck().getHealthUrl());
            } else {
                ServiceHealthCheck serviceHealthCheck = new ServiceHealthCheck();
                serviceHealthCheck.setHealthUrl(dto.getHealthCheck().getHealthUrl());
                service.setHealthCheck(serviceHealthCheck);
            }
        } else {
            service.setHealthCheck(null);
        }
    
        try {
            em.getTransaction().begin();
            em.merge(service);
            em.getTransaction().commit();
            return service;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new TaskerException("Error saving entity!");
        }
    }
    
    @Override
    public ServiceToken generateServiceToken(long serviceId) {
        Service service = getServiceById(serviceId);
        if (service == null) {
            throw new ServiceNotFoundException(serviceId);
        }
        String token = RandomStringGenerator.generate(30);
        
        try {
            em.getTransaction().begin();
            service.setToken(BCrypt.hashpw(token, BCrypt.gensalt()));
            em.merge(service);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
        return new ServiceToken(token);
    }
    
    @Override
    public void doHealthCheck(long serviceId) {
        Service service = getServiceById(serviceId);
        if (service == null) {
            throw new ServiceNotFoundException(serviceId);
        }
        ServiceHealthCheck healthCheck = service.getHealthCheck();
        if (healthCheck == null) {
            throw new MissingHealthCheckException();
        }
        Response response = HttpClient.get(healthCheck.getHealthUrl());
        if (response.getStatus() >= 400) {
            throw new FailedHealthCheckException(service.getName());
        }
    }
    
    @Override
    public void deleteService(long serviceId) {
        Service service = getServiceById(serviceId);
        if (service == null) {
            throw new ServiceNotFoundException(serviceId);
        }
        try {
            em.getTransaction().begin();
            service.setActive(false);
            em.merge(service);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
    
    @Override
    public DockerContainerInfo getServiceContainer(long serviceId) {
        Service service = getDeployedService(serviceId);
        
        return dockerService.getContainerInfo(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerDaemon()
        );
    }
    
    @Override
    public String getRawServiceContainer(long serviceId) {
        Service service = getDeployedService(serviceId);
        
        return dockerService.getRawContainerInfo(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerDaemon()
        );
    }
    
    @Override
    public DockerState getContainerState(long serviceId) {
        Service service = getDeployedService(serviceId);
        
        DockerContainerInfo containerInfo = dockerService.getContainerInfo(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerDaemon()
        );
        
        return DockerMapper.fromInfoToState(containerInfo);
    }
    
    @Override
    public void startContainer(long serviceId) {
        Service service = getDeployedService(serviceId);
        dockerService.startContainer(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerDaemon()
        );
    }
    
    @Override
    public void stopContainer(long serviceId) {
        Service service = getDeployedService(serviceId);
        dockerService.stopContainer(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerDaemon()
        );
    }
    
    @Override
    public void recreateContainer(long serviceId) {
        Service service = getDeployedService(serviceId);
        String newContainerId = dockerService.recreateContainer(
            service.getDeployment().getContainerId(),
            service.getDeployment().getDockerDaemon()
        );
        
        service.getDeployment().setContainerId(newContainerId);
        
        try {
            em.getTransaction().begin();
            em.merge(service);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
    
    private Service getDeployedService(long serviceId) {
        Service service = getServiceById(serviceId);
        if (service == null) {
            throw new EntityNotFoundException("Service not found!");
        }
        
        if (service.getDeployment() == null) {
            throw new NotDeployedException("Service is not deployed on docker.");
        }
        return service;
    }
}
