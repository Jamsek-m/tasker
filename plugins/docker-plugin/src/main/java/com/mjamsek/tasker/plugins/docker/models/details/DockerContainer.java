package com.mjamsek.tasker.plugins.docker.models.details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

// @JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainer implements Serializable {
    
    @JsonProperty("Id")
    private String Id;
    
    @JsonProperty("Created")
    private Date Created;
    
    @JsonProperty("Path")
    private String Path;
    
    @JsonProperty("Args")
    private String[] Args;
    
    @JsonProperty("State")
    private DockerContainerState State;
    
    @JsonProperty("Image")
    private String Image;
    
    @JsonProperty("ResolvConfPath")
    private String ResolvConfPath;
    
    @JsonProperty("HostnamePath")
    private String HostnamePath;
    
    @JsonProperty("HostsPath")
    private String HostsPath;
    
    @JsonProperty("LogPath")
    private String LogPath;
    
    @JsonProperty("Name")
    private String Name;
    
    @JsonProperty("RestartCount")
    private Integer RestartCount;
    
    @JsonProperty("HostConfig")
    private DockerContainerHostConfig HostConfig;
    
    @JsonProperty("Config")
    private DockerContainerConfig Config;
    
    @JsonProperty("NetworkSettings")
    private DockerContainerNetworkSettings NetworkSettings;
    
    public String getId() {
        return Id;
    }
    
    public void setId(String id) {
        Id = id;
    }
    
    public Date getCreated() {
        return Created;
    }
    
    public void setCreated(Date created) {
        Created = created;
    }
    
    public String getPath() {
        return Path;
    }
    
    public void setPath(String path) {
        Path = path;
    }
    
    public String[] getArgs() {
        return Args;
    }
    
    public void setArgs(String[] args) {
        Args = args;
    }
    
    public DockerContainerState getState() {
        return State;
    }
    
    public void setState(DockerContainerState state) {
        State = state;
    }
    
    public String getImage() {
        return Image;
    }
    
    public void setImage(String image) {
        Image = image;
    }
    
    public String getResolvConfPath() {
        return ResolvConfPath;
    }
    
    public void setResolvConfPath(String resolvConfPath) {
        ResolvConfPath = resolvConfPath;
    }
    
    public String getHostnamePath() {
        return HostnamePath;
    }
    
    public void setHostnamePath(String hostnamePath) {
        HostnamePath = hostnamePath;
    }
    
    public String getHostsPath() {
        return HostsPath;
    }
    
    public void setHostsPath(String hostsPath) {
        HostsPath = hostsPath;
    }
    
    public String getLogPath() {
        return LogPath;
    }
    
    public void setLogPath(String logPath) {
        LogPath = logPath;
    }
    
    public String getName() {
        return Name;
    }
    
    public void setName(String name) {
        Name = name;
    }
    
    public Integer getRestartCount() {
        return RestartCount;
    }
    
    public void setRestartCount(Integer restartCount) {
        RestartCount = restartCount;
    }
    
    public DockerContainerHostConfig getHostConfig() {
        return HostConfig;
    }
    
    public void setHostConfig(DockerContainerHostConfig hostConfig) {
        HostConfig = hostConfig;
    }
    
    public DockerContainerConfig getConfig() {
        return Config;
    }
    
    public void setConfig(DockerContainerConfig config) {
        Config = config;
    }
    
    public DockerContainerNetworkSettings getNetworkSettings() {
        return NetworkSettings;
    }
    
    public void setNetworkSettings(DockerContainerNetworkSettings networkSettings) {
        NetworkSettings = networkSettings;
    }
    
    
    @Override
    public String toString() {
        return "{\"_class\":\"DockerContainer\", " +
            "\"Id\":" + (Id == null ? "null" : "\"" + Id + "\"") + ", " +
            "\"Image\":" + (Image == null ? "null" : "\"" + Image + "\"") + ", " +
            "\"Name\":" + (Name == null ? "null" : "\"" + Name + "\"") +
            "}";
    }
}
