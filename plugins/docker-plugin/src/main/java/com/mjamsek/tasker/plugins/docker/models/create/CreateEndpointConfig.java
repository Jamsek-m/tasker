package com.mjamsek.tasker.plugins.docker.models.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateEndpointConfig {
    
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
