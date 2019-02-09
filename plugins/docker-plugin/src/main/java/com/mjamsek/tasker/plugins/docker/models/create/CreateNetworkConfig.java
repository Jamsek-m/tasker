package com.mjamsek.tasker.plugins.docker.models.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class CreateNetworkConfig {
    
    @JsonProperty("EndpointsConfig")
    private HashMap<String, CreateEndpointConfig> EndpointsConfig;
    
    public HashMap<String, CreateEndpointConfig> getEndpointsConfig() {
        return EndpointsConfig;
    }
    
    public void setEndpointsConfig(HashMap<String, CreateEndpointConfig> endpointsConfig) {
        EndpointsConfig = endpointsConfig;
    }
}
