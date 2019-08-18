package com.mjamsek.tasker.lib.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceDeployment {
    
    private String id;
    
    private String containerId;
    
    private String containerName;
    
    private DockerEndpoint dockerEndpoint;
    
    public String getContainerId() {
        return containerId;
    }
    
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
    
    public String getContainerName() {
        return containerName;
    }
    
    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }
    
    public DockerEndpoint getDockerEndpoint() {
        return dockerEndpoint;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setDockerEndpoint(DockerEndpoint dockerEndpoint) {
        this.dockerEndpoint = dockerEndpoint;
    }
}
