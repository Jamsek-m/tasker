package com.mjamsek.tasker.plugins.docker.models.details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainerNetwork {
    
    @JsonProperty("NetworkID")
    private String NetworkID;
    
    @JsonProperty("IPAddress")
    private String IPAddress;
    
    @JsonProperty("MacAddress")
    private String MacAddress;
    
    public String getNetworkID() {
        return NetworkID;
    }
    
    public void setNetworkID(String networkID) {
        NetworkID = networkID;
    }
    
    public String getIPAddress() {
        return IPAddress;
    }
    
    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }
    
    public String getMacAddress() {
        return MacAddress;
    }
    
    public void setMacAddress(String macAddress) {
        MacAddress = macAddress;
    }
}
