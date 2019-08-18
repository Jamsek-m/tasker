package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.lib.v1.integration.docker.DockerContainerInfo;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerCreateContainer;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DockerMapper {
    
    public static DockerState fromInfoToState(DockerContainerInfo containerInfo) {
        DockerState state = new DockerState();
        state.setDead(containerInfo.state.dead);
        state.setPaused(containerInfo.state.paused);
        state.setRestarting(containerInfo.state.restarting);
        state.setRunning(containerInfo.state.running);
        state.setStatus(containerInfo.state.status);
        return state;
    }
    
    public static DockerCreateContainer fromInfoToCreate(DockerContainerInfo info) {
        DockerCreateContainer c = new DockerCreateContainer();
    
        c.hostname = info.config.hostname;
        c.domainname = info.config.domainname;
        c.user = info.config.user;
        c.cmd = info.config.cmd;
        c.env = info.config.env;
        c.image = info.image;
        c.labels = info.config.labels;
        c.volumes = info.config.volumes;
        c.workingDir = info.config.workingDir;
        c.macAddress = info.networkSettings.macAddress;
        c.exposedPorts = info.config.exposedPorts;
        
        DockerCreateContainer.HostConfig hc = new DockerCreateContainer.HostConfig();
    
        hc.binds = info.hostConfig.binds;
        hc.memory = info.hostConfig.memory;
        hc.memorySwap = info.hostConfig.memorySwap;
        hc.memoryReservation = info.hostConfig.memoryReservation;
        hc.kernelMemory = info.hostConfig.kernelMemory;
        hc.nanoCPUs = info.hostConfig.nanoCpus;
        hc.cpuPercent = info.hostConfig.cpuPercent;
        hc.cpuShares = info.hostConfig.cpuShares;
        hc.cpuPeriod = info.hostConfig.cpuPeriod;
        hc.cpuRealtimePeriod = info.hostConfig.cpuRealtimePeriod;
        hc.cpuRealtimeRuntime = info.hostConfig.cpuRealtimeRuntime;
        hc.cpuQuota = info.hostConfig.cpuQuota;
        hc.cpusetCpus = info.hostConfig.cpusetCpus;
        hc.cpusetMems = info.hostConfig.cpusetMems;
        hc.maximumIOps = info.hostConfig.iOMaximumIOps;
        hc.oomKillDisable = info.hostConfig.oomKillDisable;
        hc.oomScoreAdj = info.hostConfig.oomScoreAdj;
        hc.pidMode = info.hostConfig.pidMode;
        hc.pidsLimit = info.hostConfig.pidsLimit;
        
        hc.portBindings = new HashMap<>();
        for(String port : info.hostConfig.portBindings.keySet()) {
            List<DockerContainerInfo.Port> portData = info.hostConfig.portBindings.get(port);
            ArrayList<DockerCreateContainer.PortBinding> mappedPorts = new ArrayList<>();
            for(DockerContainerInfo.Port portEntry : portData) {
                DockerCreateContainer.PortBinding p = new DockerCreateContainer.PortBinding();
                p.hostPort = portEntry.hostPort;
                mappedPorts.add(p);
            }
            hc.portBindings.put(port, mappedPorts);
        }
        
        hc.publishAllPorts = info.hostConfig.publishAllPorts;
        hc.privileged = info.hostConfig.privileged;
        hc.readonlyRootfs = info.hostConfig.readonlyRootfs;
        hc.dns = info.hostConfig.dns;
        hc.dnsOptions = info.hostConfig.dnsOptions;
        hc.dnsSearch = info.hostConfig.dnsSearch;
        hc.restartPolicy = info.hostConfig.restartPolicy;
        hc.autoRemove = info.hostConfig.autoRemove;
        hc.networkMode = info.hostConfig.networkMode;
        hc.devices = info.hostConfig.devices;
        hc.logConfig = info.hostConfig.logConfig;
        hc.shmSize = info.hostConfig.shmSize;
        
        c.hostConfig = hc;
        
        
        DockerCreateContainer.NetworkingConfig n = new DockerCreateContainer.NetworkingConfig();
        n.endpointsConfig = new HashMap<>();
        for(String networkName : info.networkSettings.networks.keySet()) {
            DockerContainerInfo.Network net = info.networkSettings.networks.get(networkName);
            DockerCreateContainer.EndpointConfig ec = new DockerCreateContainer.EndpointConfig();
            ec.aliases = net.aliases;
            ec.links = net.links;
            ec.IPAMConfig = net.iPAMConfig;
            
            n.endpointsConfig.put(networkName, ec);
        }
        c.networkingConfig = n;
    
        return c;
    }
}
