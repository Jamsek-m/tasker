package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryFilter;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.enums.FilterOperation;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.exceptions.FailedHealthCheckException;
import com.mjamsek.tasker.entities.exceptions.ServiceNotFoundException;
import com.mjamsek.tasker.entities.persistence.service.Service;
import com.mjamsek.tasker.entities.persistence.service.ServiceHealthCheck;
import com.mjamsek.tasker.services.ServicesService;
import com.mjamsek.tasker.utils.HttpClient;

import javax.enterprise.context.ApplicationScoped;
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
    public Service updateService(Service service) {
        return null;
    }
    
    @Override
    public void doHealthCheck(long serviceId) {
        Service service = getServiceById(serviceId);
        if (service == null) {
            throw new ServiceNotFoundException(serviceId);
        }
        ServiceHealthCheck healthCheck = service.getHealthCheck();
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
