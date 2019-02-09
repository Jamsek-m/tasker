package com.mjamsek.tasker.plugins.docker.models.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;


public class CreateContainerRequest {
    
    @JsonProperty("Hostname")
    private String Hostname;

    @JsonProperty("Domainname")
    private String Domainname;
    
    @JsonProperty("User")
    private String User;
    
    @JsonProperty("ExposedPorts")
    private HashMap<String, Object> ExposedPorts;
    
    @JsonProperty("Env")
    private List<String> Env;
    
    @JsonProperty("Image")
    private String Image;
    
    @JsonProperty("WorkingDir")
    private String WorkingDir;
    
    @JsonProperty("Volumes")
    private HashMap<String, Object> Volumes;
    
    @JsonProperty("HostConfig")
    private CreateHostConfig HostConfig;
    
    @JsonProperty("NetworkingConfig")
    private CreateNetworkConfig NetworkingConfig;
    
    public String getHostname() {
        return Hostname;
    }
    
    public void setHostname(String hostname) {
        Hostname = hostname;
    }
    
    public String getDomainname() {
        return Domainname;
    }
    
    public void setDomainname(String domainname) {
        Domainname = domainname;
    }
    
    public String getUser() {
        return User;
    }
    
    public void setUser(String user) {
        User = user;
    }
    
    public HashMap<String, Object> getExposedPorts() {
        return ExposedPorts;
    }
    
    public void setExposedPorts(HashMap<String, Object> exposedPorts) {
        ExposedPorts = exposedPorts;
    }
    
    public List<String> getEnv() {
        return Env;
    }
    
    public void setEnv(List<String> env) {
        Env = env;
    }
    
    public String getImage() {
        return Image;
    }
    
    public void setImage(String image) {
        Image = image;
    }
    
    public String getWorkingDir() {
        return WorkingDir;
    }
    
    public void setWorkingDir(String workingDir) {
        WorkingDir = workingDir;
    }
    
    public HashMap<String, Object> getVolumes() {
        return Volumes;
    }
    
    public void setVolumes(HashMap<String, Object> volumes) {
        Volumes = volumes;
    }
    
    public CreateHostConfig getHostConfig() {
        return HostConfig;
    }
    
    public void setHostConfig(CreateHostConfig hostConfig) {
        HostConfig = hostConfig;
    }
    
    public CreateNetworkConfig getNetworkingConfig() {
        return NetworkingConfig;
    }
    
    public void setNetworkingConfig(CreateNetworkConfig networkingConfig) {
        NetworkingConfig = networkingConfig;
    }
}
