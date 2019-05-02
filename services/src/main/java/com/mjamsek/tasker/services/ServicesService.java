package com.mjamsek.tasker.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.entities.dto.ServiceRequest;
import com.mjamsek.tasker.entities.dto.ServiceToken;
import com.mjamsek.tasker.entities.persistence.service.Service;

import java.util.List;

public interface ServicesService {
    
    List<Service> getServices(QueryParameters queryParameters);
    
    long getServicesCount(QueryParameters queryParameters);
    
    Service getServiceByName(String name);
    
    Service getServiceById(long serviceId);
    
    Service getServiceByIdOrName(String idOrName);
    
    Service createService(ServiceRequest dto);
    
    Service updateService(Service dto);
    
    ServiceToken generateServiceToken(long serviceId);
    
    void doHealthCheck(long serviceId);
    
    void startService(long serviceId);
    
    void stopService(long serviceId);
    
    void recreateServiceContainer(long serviceId);
    
    void deleteService(long serviceId);
    
}
