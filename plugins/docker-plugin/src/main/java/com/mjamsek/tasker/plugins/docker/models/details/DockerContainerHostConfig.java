package com.mjamsek.tasker.plugins.docker.models.details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainerHostConfig {
    
    @JsonProperty("PortBindings")
    private HashMap<String, List<DockerContainerPortBindings>> PortBindings;
    
    @JsonProperty("RestartPolicy")
    private DockerContainerRestartPolicy RestartPolicy;
    
    public HashMap<String, List<DockerContainerPortBindings>> getPortBindings() {
        return PortBindings;
    }
    
    public void setPortBindings(HashMap<String, List<DockerContainerPortBindings>> portBindings) {
        PortBindings = portBindings;
    }
    
    public DockerContainerRestartPolicy getRestartPolicy() {
        return RestartPolicy;
    }
    
    public void setRestartPolicy(DockerContainerRestartPolicy restartPolicy) {
        RestartPolicy = restartPolicy;
    }
}
