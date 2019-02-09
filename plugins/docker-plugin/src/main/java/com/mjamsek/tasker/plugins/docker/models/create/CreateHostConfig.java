package com.mjamsek.tasker.plugins.docker.models.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjamsek.tasker.plugins.docker.models.details.DockerContainerPortBindings;
import com.mjamsek.tasker.plugins.docker.models.details.DockerContainerRestartPolicy;

import java.util.HashMap;
import java.util.List;

public class CreateHostConfig {
    
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
