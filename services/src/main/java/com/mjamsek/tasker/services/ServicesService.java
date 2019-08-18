package com.mjamsek.tasker.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.entities.dto.ServiceToken;
import com.mjamsek.tasker.entities.persistence.service.ServiceEntity;
import com.mjamsek.tasker.lib.v1.Service;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerContainerInfo;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerState;

import java.util.List;

public interface ServicesService {
    
    List<Service> getServices(QueryParameters queryParameters);
    
    long getServicesCount(QueryParameters queryParameters);
    
    Service getService(String serviceId);
    
    ServiceEntity getServiceByName(String name);
    
    /**
     * @param serviceId
     * @return Service if found, null otherwise
     */
    ServiceEntity getServiceById(String serviceId);
    
    Service getServiceByIdOrName(String idOrName);
    
    Service createService(Service service);
    
    Service updateService(Service service, String serviceId);
    
    ServiceToken generateServiceToken(String serviceId);
    
    void doHealthCheck(String serviceId);
    
    void deleteService(String serviceId);
    
    DockerContainerInfo getServiceContainer(String serviceId);
    
    String getRawServiceContainer(String serviceId);
    
    DockerState getContainerState(String serviceId);
    
    void startContainer(String serviceId);
    
    void stopContainer(String serviceId);
    
    void recreateContainer(String serviceId);
    
}
