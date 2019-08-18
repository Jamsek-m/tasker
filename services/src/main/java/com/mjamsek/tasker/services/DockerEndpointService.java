package com.mjamsek.tasker.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.entities.persistence.service.DockerEndpointEntity;
import com.mjamsek.tasker.lib.v1.DockerEndpoint;

import java.util.List;

public interface DockerEndpointService {
    
    List<DockerEndpoint> getEndpoints(QueryParameters queryParameters);
    
    long getEndpointsCount(QueryParameters queryParameters);
    
    DockerEndpointEntity getDockerEndpoint(String endpointId);
    
    DockerEndpointEntity getDockerEndpointByName(String endpointName);
    
    DockerEndpoint saveEndpoint(DockerEndpoint endpoint);
    
    DockerEndpoint updateEndpoint(DockerEndpoint endpoint, String endpointId);
    
    void deleteEndpoint(String endpointId);
}
