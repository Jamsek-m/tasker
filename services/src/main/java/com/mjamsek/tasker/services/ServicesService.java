package com.mjamsek.tasker.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.entities.docker.DockerContainerInfo;
import com.mjamsek.tasker.entities.docker.DockerState;
import com.mjamsek.tasker.entities.dto.ServiceRequest;
import com.mjamsek.tasker.entities.dto.ServiceToken;
import com.mjamsek.tasker.entities.persistence.service.Service;

import java.util.List;

public interface ServicesService {
    
    List<Service> getServices(QueryParameters queryParameters);
    
    long getServicesCount(QueryParameters queryParameters);
    
    Service getServiceByName(String name);
    
    /**
     *
     * @param serviceId
     * @return Service if found, null otherwise
     */
    Service getServiceById(long serviceId);
    
    Service getServiceByIdOrName(String idOrName);
    
    Service createService(ServiceRequest dto);
    
    Service updateService(ServiceRequest dto, long serviceId);
    
    ServiceToken generateServiceToken(long serviceId);
    
    void doHealthCheck(long serviceId);
    
    void deleteService(long serviceId);
    
    DockerContainerInfo getServiceContainer(long serviceId);
    
    String getRawServiceContainer(long serviceId);
    
    DockerState getContainerState(long serviceId);
    
    void startContainer(long serviceId);
    
    void stopContainer(long serviceId);
    
    void recreateContainer(long serviceId);
    
}
