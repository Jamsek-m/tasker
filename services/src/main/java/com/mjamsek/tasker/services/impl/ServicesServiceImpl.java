package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.entities.persistence.service.Service;
import com.mjamsek.tasker.services.ServicesService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ServicesServiceImpl implements ServicesService {
    
    @Override
    public List<Service> getServices(QueryParameters queryParameters) {
        return null;
    }
    
    @Override
    public long getServicesCount(QueryParameters queryParameters) {
        return 0;
    }
    
    @Override
    public Service getServiceByName(String name) {
        return null;
    }
    
    @Override
    public Service getServiceById(long serviceId) {
        return null;
    }
    
    @Override
    public Service updateService(Service service) {
        return null;
    }
    
    @Override
    public void doHealthCheck(long serviceId) {
    
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
    
    }
}
