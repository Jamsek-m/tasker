package com.mjamsek.tasker.services;

import com.mjamsek.tasker.entities.exceptions.FailedHealthCheckException;
import com.mjamsek.tasker.entities.persistence.service.DockerEndpointEntity;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerContainerInfo;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerCreateContainer;

import java.util.List;

public interface DockerService {
    
    void checkEndpointAvailability() throws FailedHealthCheckException;
    
    List<DockerContainerInfo> queryContainersByName(String name, String endpointId);
    
    String getRawContainerInfo(String containerId, DockerEndpointEntity endpoint);
    
    DockerContainerInfo getContainerInfo(String containerId, DockerEndpointEntity endpoint);
    
    void startContainer(String containerId, DockerEndpointEntity endpoint);
    
    void stopContainer(String containerId, DockerEndpointEntity endpoint);
    
    void deleteContainer(String containerId, DockerEndpointEntity endpoint);
    
    String createContainer(String containerName, DockerEndpointEntity endpoint, DockerCreateContainer data);
    
    String recreateContainer(String containerId, DockerEndpointEntity endpoint);
    
}
