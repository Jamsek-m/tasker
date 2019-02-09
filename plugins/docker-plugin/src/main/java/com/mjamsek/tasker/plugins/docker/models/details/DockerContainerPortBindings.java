package com.mjamsek.tasker.plugins.docker.models.details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainerPortBindings {
    
    @JsonProperty("HostIp")
    private String HostIp;
    
    @JsonProperty("HostPort")
    private String HostPort;
    
    public String getHostIp() {
        return HostIp;
    }
    
    public void setHostIp(String hostIp) {
        HostIp = hostIp;
    }
    
    public String getHostPort() {
        return HostPort;
    }
    
    public void setHostPort(String hostPort) {
        HostPort = hostPort;
    }
}
