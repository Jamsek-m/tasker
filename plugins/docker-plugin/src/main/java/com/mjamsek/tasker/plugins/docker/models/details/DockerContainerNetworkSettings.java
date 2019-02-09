package com.mjamsek.tasker.plugins.docker.models.details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainerNetworkSettings {
    
    @JsonProperty("Bridge")
    private String Bridge;
    
    @JsonProperty("Networks")
    private HashMap<String, DockerContainerNetwork> Networks;
    
    public String getBridge() {
        return Bridge;
    }
    
    public void setBridge(String bridge) {
        Bridge = bridge;
    }
    
    public HashMap<String, DockerContainerNetwork> getNetworks() {
        return Networks;
    }
    
    public void setNetworks(HashMap<String, DockerContainerNetwork> networks) {
        Networks = networks;
    }
}
