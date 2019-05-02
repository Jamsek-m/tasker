package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryFilter;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.enums.FilterOperation;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.dto.ServiceRequest;
import com.mjamsek.tasker.entities.dto.ServiceToken;
import com.mjamsek.tasker.entities.exceptions.*;
import com.mjamsek.tasker.entities.persistence.service.*;
import com.mjamsek.tasker.services.DockerDaemonService;
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
    public Service updateService(Service dto) {
        return null;
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
    public void startService(long serviceId) {
    
    }
    
    @Override
    public void stopService(long serviceId) {
    
    }
    
    @Override
    public void recreateServiceContainer(long serviceId) {
    
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
}
