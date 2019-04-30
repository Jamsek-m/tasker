package com.mjamsek.tasker.entities.docker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainerInfo {
    
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Names")
    private List<String> names = null;
    @JsonProperty("Image")
    private String image;
    @JsonProperty("ImageID")
    private String imageID;
    @JsonProperty("Command")
    private String command;
    @JsonProperty("Created")
    private Integer created;
    @JsonProperty("Ports")
    private List<Port> ports = null;
    @JsonProperty("Labels")
    private Map<String, Object> labels;
    @JsonProperty("State")
    private String state;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("HostConfig")
    private HostConfig hostConfig;
    @JsonProperty("NetworkSettings")
    private NetworkSettings networkSettings;
    @JsonProperty("Mounts")
    private List<Object> mounts = null;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public List<String> getNames() {
        return names;
    }
    
    public void setNames(List<String> names) {
        this.names = names;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getImageID() {
        return imageID;
    }
    
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }
    
    public String getCommand() {
        return command;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }
    
    public Integer getCreated() {
        return created;
    }
    
    public void setCreated(Integer created) {
        this.created = created;
    }
    
    public List<Port> getPorts() {
        return ports;
    }
    
    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }
    
    public Map<String, Object> getLabels() {
        return labels;
    }
    
    public void setLabels(Map<String, Object> labels) {
        this.labels = labels;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public HostConfig getHostConfig() {
        return hostConfig;
    }
    
    public void setHostConfig(HostConfig hostConfig) {
        this.hostConfig = hostConfig;
    }
    
    public NetworkSettings getNetworkSettings() {
        return networkSettings;
    }
    
    public void setNetworkSettings(NetworkSettings networkSettings) {
        this.networkSettings = networkSettings;
    }
    
    public List<Object> getMounts() {
        return mounts;
    }
    
    public void setMounts(List<Object> mounts) {
        this.mounts = mounts;
    }
    
    public static class Bridge {
        @JsonProperty("IPAMConfig")
        private Object iPAMConfig;
        @JsonProperty("Links")
        private Object links;
        @JsonProperty("Aliases")
        private Object aliases;
        @JsonProperty("NetworkID")
        private String networkID;
        @JsonProperty("EndpointID")
        private String endpointID;
        @JsonProperty("Gateway")
        private String gateway;
        @JsonProperty("IPAddress")
        private String iPAddress;
        @JsonProperty("IPPrefixLen")
        private Integer iPPrefixLen;
        @JsonProperty("IPv6Gateway")
        private String iPv6Gateway;
        @JsonProperty("GlobalIPv6Address")
        private String globalIPv6Address;
        @JsonProperty("GlobalIPv6PrefixLen")
        private Integer globalIPv6PrefixLen;
        @JsonProperty("MacAddress")
        private String macAddress;
        @JsonProperty("DriverOpts")
        private Object driverOpts;
        
        public Object getiPAMConfig() {
            return iPAMConfig;
        }
        
        public void setiPAMConfig(Object iPAMConfig) {
            this.iPAMConfig = iPAMConfig;
        }
        
        public Object getLinks() {
            return links;
        }
        
        public void setLinks(Object links) {
            this.links = links;
        }
        
        public Object getAliases() {
            return aliases;
        }
        
        public void setAliases(Object aliases) {
            this.aliases = aliases;
        }
        
        public String getNetworkID() {
            return networkID;
        }
        
        public void setNetworkID(String networkID) {
            this.networkID = networkID;
        }
        
        public String getEndpointID() {
            return endpointID;
        }
        
        public void setEndpointID(String endpointID) {
            this.endpointID = endpointID;
        }
        
        public String getGateway() {
            return gateway;
        }
        
        public void setGateway(String gateway) {
            this.gateway = gateway;
        }
        
        public String getiPAddress() {
            return iPAddress;
        }
        
        public void setiPAddress(String iPAddress) {
            this.iPAddress = iPAddress;
        }
        
        public Integer getiPPrefixLen() {
            return iPPrefixLen;
        }
        
        public void setiPPrefixLen(Integer iPPrefixLen) {
            this.iPPrefixLen = iPPrefixLen;
        }
        
        public String getiPv6Gateway() {
            return iPv6Gateway;
        }
        
        public void setiPv6Gateway(String iPv6Gateway) {
            this.iPv6Gateway = iPv6Gateway;
        }
        
        public String getGlobalIPv6Address() {
            return globalIPv6Address;
        }
        
        public void setGlobalIPv6Address(String globalIPv6Address) {
            this.globalIPv6Address = globalIPv6Address;
        }
        
        public Integer getGlobalIPv6PrefixLen() {
            return globalIPv6PrefixLen;
        }
        
        public void setGlobalIPv6PrefixLen(Integer globalIPv6PrefixLen) {
            this.globalIPv6PrefixLen = globalIPv6PrefixLen;
        }
        
        public String getMacAddress() {
            return macAddress;
        }
        
        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }
        
        public Object getDriverOpts() {
            return driverOpts;
        }
        
        public void setDriverOpts(Object driverOpts) {
            this.driverOpts = driverOpts;
        }
    }
    
    public static class HostConfig {
        @JsonProperty("NetworkMode")
        private String networkMode;
        
        public String getNetworkMode() {
            return networkMode;
        }
        
        public void setNetworkMode(String networkMode) {
            this.networkMode = networkMode;
        }
    }
    
    public static class NetworkSettings {
        
        @JsonProperty("Networks")
        private Networks networks;
        
        public Networks getNetworks() {
            return networks;
        }
        
        public void setNetworks(Networks networks) {
            this.networks = networks;
        }
        
        public static class Networks {
            @JsonProperty("bridge")
            private Bridge bridge;
            
            public Bridge getBridge() {
                return bridge;
            }
            
            public void setBridge(Bridge bridge) {
                this.bridge = bridge;
            }
        }
    }
    
    public static class Port {
        @JsonProperty("IP")
        private String iP;
        @JsonProperty("PrivatePort")
        private Integer privatePort;
        @JsonProperty("PublicPort")
        private Integer publicPort;
        @JsonProperty("Type")
        private String type;
        
        public String getiP() {
            return iP;
        }
        
        public void setiP(String iP) {
            this.iP = iP;
        }
        
        public Integer getPrivatePort() {
            return privatePort;
        }
        
        public void setPrivatePort(Integer privatePort) {
            this.privatePort = privatePort;
        }
        
        public Integer getPublicPort() {
            return publicPort;
        }
        
        public void setPublicPort(Integer publicPort) {
            this.publicPort = publicPort;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
    }
}
