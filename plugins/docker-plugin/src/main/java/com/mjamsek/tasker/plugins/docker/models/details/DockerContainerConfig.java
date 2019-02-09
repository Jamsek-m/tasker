package com.mjamsek.tasker.plugins.docker.models.details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainerConfig {
    
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
    
    @JsonProperty("Cmd")
    private List<String> Cmd;
    
    @JsonProperty("Image")
    private String Image;
    
    @JsonProperty("Volumes")
    private HashMap<String, Object> Volumes;
    
    @JsonProperty("WorkingDir")
    private String WorkingDir;
    
    @JsonProperty("Labels")
    private HashMap<String, String> Labels;
    
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
    
    public List<String> getCmd() {
        return Cmd;
    }
    
    public void setCmd(List<String> cmd) {
        Cmd = cmd;
    }
    
    public String getImage() {
        return Image;
    }
    
    public void setImage(String image) {
        Image = image;
    }
    
    public HashMap<String, Object> getVolumes() {
        return Volumes;
    }
    
    public void setVolumes(HashMap<String, Object> volumes) {
        Volumes = volumes;
    }
    
    public String getWorkingDir() {
        return WorkingDir;
    }
    
    public void setWorkingDir(String workingDir) {
        WorkingDir = workingDir;
    }
    
    public HashMap<String, String> getLabels() {
        return Labels;
    }
    
    public void setLabels(HashMap<String, String> labels) {
        Labels = labels;
    }
}
