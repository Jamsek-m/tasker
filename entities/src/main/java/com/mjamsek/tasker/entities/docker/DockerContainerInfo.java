package com.mjamsek.tasker.entities.docker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainerInfo {
    
    @JsonProperty("Id")
    public String id;
    @JsonProperty("Created")
    public String created;
    @JsonProperty("Path")
    public String path;
    @JsonProperty("Args")
    public List<String> args = null;
    @JsonProperty("State")
    public State state;
    @JsonProperty("Image")
    public String image;
    @JsonProperty("ResolvConfPath")
    public String resolvConfPath;
    @JsonProperty("HostnamePath")
    public String hostnamePath;
    @JsonProperty("HostsPath")
    public String hostsPath;
    @JsonProperty("LogPath")
    public String logPath;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("RestartCount")
    public int restartCount;
    @JsonProperty("Driver")
    public String driver;
    @JsonProperty("Platform")
    public String platform;
    @JsonProperty("MountLabel")
    public String mountLabel;
    @JsonProperty("ProcessLabel")
    public String processLabel;
    @JsonProperty("AppArmorProfile")
    public String appArmorProfile;
    @JsonProperty("ExecIDs")
    public Object execIDs;
    @JsonProperty("HostConfig")
    public HostConfig hostConfig;
    @JsonProperty("GraphDriver")
    public GraphDriver graphDriver;
    @JsonProperty("Mounts")
    public List<Mount> mounts = null;
    @JsonProperty("Config")
    public Config config;
    @JsonProperty("NetworkSettings")
    public NetworkSettings networkSettings;
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Config {
        @JsonProperty("Hostname")
        public String hostname;
        @JsonProperty("Domainname")
        public String domainname;
        @JsonProperty("User")
        public String user;
        @JsonProperty("AttachStdin")
        public boolean attachStdin;
        @JsonProperty("AttachStdout")
        public boolean attachStdout;
        @JsonProperty("AttachStderr")
        public boolean attachStderr;
        @JsonProperty("ExposedPorts")
        public Map<String, Object> exposedPorts;
        @JsonProperty("Tty")
        public boolean tty;
        @JsonProperty("OpenStdin")
        public boolean openStdin;
        @JsonProperty("StdinOnce")
        public boolean stdinOnce;
        @JsonProperty("Env")
        public List<String> env = null;
        @JsonProperty("Cmd")
        public List<String> cmd = null;
        @JsonProperty("ArgsEscaped")
        public boolean argsEscaped;
        @JsonProperty("Image")
        public String image;
        @JsonProperty("Volumes")
        public Map<String, Object> volumes;
        @JsonProperty("WorkingDir")
        public String workingDir;
        @JsonProperty("Entrypoint")
        public List<String> entrypoint = null;
        @JsonProperty("OnBuild")
        public Object onBuild;
        @JsonProperty("Labels")
        public Map<String, String> labels;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @JsonProperty("LowerDir")
        public String lowerDir;
        @JsonProperty("MergedDir")
        public String mergedDir;
        @JsonProperty("UpperDir")
        public String upperDir;
        @JsonProperty("WorkDir")
        public String workDir;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GraphDriver {
        @JsonProperty("Data")
        public Data data;
        @JsonProperty("Name")
        public String name;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Network {
        @JsonProperty("IPAMConfig")
        public Object iPAMConfig;
        @JsonProperty("Links")
        public Object links;
        @JsonProperty("Aliases")
        public List<String> aliases = null;
        @JsonProperty("NetworkID")
        public String networkID;
        @JsonProperty("EndpointID")
        public String endpointID;
        @JsonProperty("Gateway")
        public String gateway;
        @JsonProperty("IPAddress")
        public String iPAddress;
        @JsonProperty("IPPrefixLen")
        public int iPPrefixLen;
        @JsonProperty("IPv6Gateway")
        public String iPv6Gateway;
        @JsonProperty("GlobalIPv6Address")
        public String globalIPv6Address;
        @JsonProperty("GlobalIPv6PrefixLen")
        public int globalIPv6PrefixLen;
        @JsonProperty("MacAddress")
        public String macAddress;
        @JsonProperty("DriverOpts")
        public Object driverOpts;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HostConfig {
        @JsonProperty("Binds")
        public List<String> binds = null;
        @JsonProperty("ContainerIDFile")
        public String containerIDFile;
        @JsonProperty("LogConfig")
        public LogConfig logConfig;
        @JsonProperty("NetworkMode")
        public String networkMode;
        @JsonProperty("PortBindings")
        public Map<String, List<Port>> portBindings;
        @JsonProperty("RestartPolicy")
        public RestartPolicy restartPolicy;
        @JsonProperty("AutoRemove")
        public boolean autoRemove;
        @JsonProperty("VolumeDriver")
        public String volumeDriver;
        @JsonProperty("VolumesFrom")
        public Object volumesFrom;
        @JsonProperty("CapAdd")
        public Object capAdd;
        @JsonProperty("CapDrop")
        public Object capDrop;
        @JsonProperty("Dns")
        public List<Object> dns = null;
        @JsonProperty("DnsOptions")
        public List<Object> dnsOptions = null;
        @JsonProperty("DnsSearch")
        public List<Object> dnsSearch = null;
        @JsonProperty("ExtraHosts")
        public List<Object> extraHosts = null;
        @JsonProperty("GroupAdd")
        public Object groupAdd;
        @JsonProperty("IpcMode")
        public String ipcMode;
        @JsonProperty("Cgroup")
        public String cgroup;
        @JsonProperty("Links")
        public Object links;
        @JsonProperty("OomScoreAdj")
        public int oomScoreAdj;
        @JsonProperty("PidMode")
        public String pidMode;
        @JsonProperty("Privileged")
        public boolean privileged;
        @JsonProperty("PublishAllPorts")
        public boolean publishAllPorts;
        @JsonProperty("ReadonlyRootfs")
        public boolean readonlyRootfs;
        @JsonProperty("SecurityOpt")
        public Object securityOpt;
        @JsonProperty("UTSMode")
        public String uTSMode;
        @JsonProperty("UsernsMode")
        public String usernsMode;
        @JsonProperty("ShmSize")
        public int shmSize;
        @JsonProperty("Runtime")
        public String runtime;
        @JsonProperty("ConsoleSize")
        public List<Integer> consoleSize = null;
        @JsonProperty("Isolation")
        public String isolation;
        @JsonProperty("CpuShares")
        public int cpuShares;
        @JsonProperty("Memory")
        public int memory;
        @JsonProperty("NanoCpus")
        public int nanoCpus;
        @JsonProperty("CgroupParent")
        public String cgroupParent;
        @JsonProperty("BlkioWeight")
        public int blkioWeight;
        @JsonProperty("BlkioWeightDevice")
        public Object blkioWeightDevice;
        @JsonProperty("BlkioDeviceReadBps")
        public Object blkioDeviceReadBps;
        @JsonProperty("BlkioDeviceWriteBps")
        public Object blkioDeviceWriteBps;
        @JsonProperty("BlkioDeviceReadIOps")
        public Object blkioDeviceReadIOps;
        @JsonProperty("BlkioDeviceWriteIOps")
        public Object blkioDeviceWriteIOps;
        @JsonProperty("CpuPeriod")
        public int cpuPeriod;
        @JsonProperty("CpuQuota")
        public int cpuQuota;
        @JsonProperty("CpuRealtimePeriod")
        public int cpuRealtimePeriod;
        @JsonProperty("CpuRealtimeRuntime")
        public int cpuRealtimeRuntime;
        @JsonProperty("CpusetCpus")
        public String cpusetCpus;
        @JsonProperty("CpusetMems")
        public String cpusetMems;
        @JsonProperty("Devices")
        public List<Object> devices = null;
        @JsonProperty("DeviceCgroupRules")
        public Object deviceCgroupRules;
        @JsonProperty("DiskQuota")
        public int diskQuota;
        @JsonProperty("KernelMemory")
        public int kernelMemory;
        @JsonProperty("MemoryReservation")
        public int memoryReservation;
        @JsonProperty("MemorySwap")
        public int memorySwap;
        @JsonProperty("MemorySwappiness")
        public Object memorySwappiness;
        @JsonProperty("OomKillDisable")
        public boolean oomKillDisable;
        @JsonProperty("PidsLimit")
        public int pidsLimit;
        @JsonProperty("Ulimits")
        public Object ulimits;
        @JsonProperty("CpuCount")
        public int cpuCount;
        @JsonProperty("CpuPercent")
        public int cpuPercent;
        @JsonProperty("IOMaximumIOps")
        public int iOMaximumIOps;
        @JsonProperty("IOMaximumBandwidth")
        public int iOMaximumBandwidth;
        @JsonProperty("MaskedPaths")
        public List<String> maskedPaths = null;
        @JsonProperty("ReadonlyPaths")
        public List<String> readonlyPaths = null;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LogConfig {
        @JsonProperty("Type")
        public String type;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Mount {
        @JsonProperty("Type")
        public String type;
        @JsonProperty("Name")
        public String name;
        @JsonProperty("Source")
        public String source;
        @JsonProperty("Destination")
        public String destination;
        @JsonProperty("Driver")
        public String driver;
        @JsonProperty("Mode")
        public String mode;
        @JsonProperty("RW")
        public boolean rW;
        @JsonProperty("Propagation")
        public String propagation;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NetworkSettings {
        @JsonProperty("Bridge")
        public String bridge;
        @JsonProperty("SandboxID")
        public String sandboxID;
        @JsonProperty("HairpinMode")
        public boolean hairpinMode;
        @JsonProperty("LinkLocalIPv6Address")
        public String linkLocalIPv6Address;
        @JsonProperty("LinkLocalIPv6PrefixLen")
        public int linkLocalIPv6PrefixLen;
        @JsonProperty("Ports")
        public Map<String, List<Port>> ports;
        @JsonProperty("SandboxKey")
        public String sandboxKey;
        @JsonProperty("SecondaryIPAddresses")
        public Object secondaryIPAddresses;
        @JsonProperty("SecondaryIPv6Addresses")
        public Object secondaryIPv6Addresses;
        @JsonProperty("EndpointID")
        public String endpointID;
        @JsonProperty("Gateway")
        public String gateway;
        @JsonProperty("GlobalIPv6Address")
        public String globalIPv6Address;
        @JsonProperty("GlobalIPv6PrefixLen")
        public int globalIPv6PrefixLen;
        @JsonProperty("IPAddress")
        public String iPAddress;
        @JsonProperty("IPPrefixLen")
        public int iPPrefixLen;
        @JsonProperty("IPv6Gateway")
        public String iPv6Gateway;
        @JsonProperty("MacAddress")
        public String macAddress;
        @JsonProperty("Networks")
        public Map<String, Network> networks;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Port {
        @JsonProperty("HostIp")
        public String hostIp;
        @JsonProperty("HostPort")
        public String hostPort;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RestartPolicy {
        @JsonProperty("Name")
        public String name;
        @JsonProperty("MaximumRetryCount")
        public int maximumRetryCount;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class State {
        @JsonProperty("Status")
        public String status;
        @JsonProperty("Running")
        public boolean running;
        @JsonProperty("Paused")
        public boolean paused;
        @JsonProperty("Restarting")
        public boolean restarting;
        @JsonProperty("OOMKilled")
        public boolean oOMKilled;
        @JsonProperty("Dead")
        public boolean dead;
        @JsonProperty("Pid")
        public int pid;
        @JsonProperty("ExitCode")
        public int exitCode;
        @JsonProperty("Error")
        public String error;
        @JsonProperty("StartedAt")
        public String startedAt;
        @JsonProperty("FinishedAt")
        public String finishedAt;
    }
}
