package com.mjamsek.tasker.plugins.docker.models.details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainerRestartPolicy {
    
    @JsonProperty("Name")
    private String Name;
    
    @JsonProperty("MaximumRetryCount")
    private Integer MaximumRetryCount;
    
    public String getName() {
        return Name;
    }
    
    public void setName(String name) {
        Name = name;
    }
    
    public Integer getMaximumRetryCount() {
        return MaximumRetryCount;
    }
    
    public void setMaximumRetryCount(Integer maximumRetryCount) {
        MaximumRetryCount = maximumRetryCount;
    }
}
