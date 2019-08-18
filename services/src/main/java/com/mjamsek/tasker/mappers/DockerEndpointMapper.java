package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.entities.persistence.service.DockerEndpointEntity;
import com.mjamsek.tasker.lib.v1.DockerEndpoint;

public class DockerEndpointMapper {
    
    public static DockerEndpointEntity toEntity(DockerEndpoint dockerEndpoint) {
        DockerEndpointEntity endpointEntity = new DockerEndpointEntity();
        endpointEntity.setId(dockerEndpoint.getId());
        endpointEntity.setName(dockerEndpoint.getName());
        endpointEntity.setUrl(dockerEndpoint.getUrl());
        return endpointEntity;
    }
    
    public static DockerEndpoint fromEntity(DockerEndpointEntity dockerEndpointEntity) {
        DockerEndpoint endpoint = new DockerEndpoint();
        endpoint.setId(dockerEndpointEntity.getId());
        endpoint.setName(dockerEndpointEntity.getName());
        endpoint.setUrl(dockerEndpointEntity.getUrl());
        return endpoint;
    }
    
}
