package com.mjamsek.tasker.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjamsek.tasker.entities.persistence.service.Service;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceRequest extends Service {
    
    @JsonProperty("isDockerized")
    private boolean isDockerized;
    @JsonProperty("isDeployed")
    private boolean isDeployed;
    @JsonProperty("hasHealthcheck")
    private boolean hasHealthcheck;
    
    public boolean isDockerized() {
        return isDockerized;
    }
    
    public void setDockerized(boolean dockerized) {
        isDockerized = dockerized;
    }
    
    public boolean isDeployed() {
        return isDeployed;
    }
    
    public void setDeployed(boolean deployed) {
        isDeployed = deployed;
    }
    
    public boolean isHasHealthcheck() {
        return hasHealthcheck;
    }
    
    public void setHasHealthcheck(boolean hasHealthcheck) {
        this.hasHealthcheck = hasHealthcheck;
    }
}
