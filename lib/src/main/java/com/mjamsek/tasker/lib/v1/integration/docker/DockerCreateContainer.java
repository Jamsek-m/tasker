package com.mjamsek.tasker.lib.v1.integration.docker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerCreateContainer {
    
    @JsonProperty("Hostname")
    public String hostname;
    @JsonProperty("Domainname")
    public String domainname;
    @JsonProperty("User")
    public String user;
    @JsonProperty("Env")
    public List<String> env = null;
    @JsonProperty("Cmd")
    public List<String> cmd = null;
    @JsonProperty("Image")
    public String image;
    @JsonProperty("Labels")
    public Map<String, String> labels;
    @JsonProperty("Volumes")
    public Map<String, Object> volumes;
    @JsonProperty("WorkingDir")
    public String workingDir;
    @JsonProperty("MacAddress")
    public String macAddress;
    @JsonProperty("ExposedPorts")
    public Map<String, Object> exposedPorts;
    @JsonProperty("HostConfig")
    public HostConfig hostConfig;
    @JsonProperty("NetworkingConfig")
    public NetworkingConfig networkingConfig;
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HostConfig {
        @JsonProperty("Binds")
        public List<String> binds = null;
        @JsonProperty("Memory")
        public int memory;
        @JsonProperty("MemorySwap")
        public int memorySwap;
        @JsonProperty("MemoryReservation")
        public int memoryReservation;
        @JsonProperty("KernelMemory")
        public int kernelMemory;
        @JsonProperty("NanoCPUs")
        public int nanoCPUs;
        @JsonProperty("CpuPercent")
        public int cpuPercent;
        @JsonProperty("CpuShares")
        public int cpuShares;
        @JsonProperty("CpuPeriod")
        public int cpuPeriod;
        @JsonProperty("CpuRealtimePeriod")
        public int cpuRealtimePeriod;
        @JsonProperty("CpuRealtimeRuntime")
        public int cpuRealtimeRuntime;
        @JsonProperty("CpuQuota")
        public int cpuQuota;
        @JsonProperty("CpusetCpus")
        public String cpusetCpus;
        @JsonProperty("CpusetMems")
        public String cpusetMems;
        @JsonProperty("MaximumIOps")
        public int maximumIOps;
        @JsonProperty("OomKillDisable")
        public boolean oomKillDisable;
        @JsonProperty("OomScoreAdj")
        public int oomScoreAdj;
        @JsonProperty("PidMode")
        public String pidMode;
        @JsonProperty("PidsLimit")
        public int pidsLimit;
        @JsonProperty("PortBindings")
        public Map<String, List<PortBinding>> portBindings;
        @JsonProperty("PublishAllPorts")
        public boolean publishAllPorts;
        @JsonProperty("Privileged")
        public boolean privileged;
        @JsonProperty("ReadonlyRootfs")
        public boolean readonlyRootfs;
        @JsonProperty("Dns")
        public List<String> dns = null;
        @JsonProperty("DnsOptions")
        public List<String> dnsOptions = null;
        @JsonProperty("DnsSearch")
        public List<String> dnsSearch = null;
        @JsonProperty("RestartPolicy")
        public DockerRestartPolicy restartPolicy;
        @JsonProperty("AutoRemove")
        public boolean autoRemove;
        @JsonProperty("NetworkMode")
        public String networkMode;
        @JsonProperty("Devices")
        public List<Object> devices = null;
        @JsonProperty("LogConfig")
        public DockerLogConfig logConfig;
        @JsonProperty("ShmSize")
        public int shmSize;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NetworkingConfig {
        @JsonProperty("EndpointsConfig")
        public Map<String, EndpointConfig> endpointsConfig;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EndpointConfig {
        @JsonProperty("IPAMConfig")
        public Object IPAMConfig;
        @JsonProperty("Links")
        public Object links;
        @JsonProperty("Aliases")
        public List<String> aliases;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PortBinding {
        @JsonProperty("HostPort")
        public String hostPort;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        @JsonProperty("Id")
        public String id;
    }
    
}
