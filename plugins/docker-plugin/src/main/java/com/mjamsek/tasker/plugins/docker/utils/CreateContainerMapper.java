package com.mjamsek.tasker.plugins.docker.utils;

import com.mjamsek.tasker.plugins.docker.actions.RecreateContainerAction;
import com.mjamsek.tasker.plugins.docker.models.create.*;
import com.mjamsek.tasker.plugins.docker.models.details.DockerContainer;
import com.mjamsek.tasker.plugins.docker.models.details.DockerContainerNetwork;
import com.mjamsek.tasker.plugins.docker.models.recreate.RecreatedContainerData;

import java.util.HashMap;

public class CreateContainerMapper {
    
    public static CreateContainerRequest fromContainerInfo(DockerContainer container) {
        CreateContainerRequest request = new CreateContainerRequest();
        
        request.setDomainname(container.getConfig().getDomainname());
        request.setHostname(container.getConfig().getHostname());
        request.setUser(container.getConfig().getUser());
        request.setEnv(container.getConfig().getEnv());
        request.setExposedPorts(container.getConfig().getExposedPorts());
        request.setImage(container.getConfig().getImage());
        request.setVolumes(container.getConfig().getVolumes());
        request.setWorkingDir(container.getConfig().getWorkingDir());
    
        CreateHostConfig hostConfig = new CreateHostConfig();
        hostConfig.setRestartPolicy(container.getHostConfig().getRestartPolicy());
        hostConfig.setPortBindings(container.getHostConfig().getPortBindings());
        request.setHostConfig(hostConfig);
    
        CreateNetworkConfig networkConfig = new CreateNetworkConfig();
        HashMap<String, CreateEndpointConfig> endpoints = new HashMap<>();
        for(String networkName : container.getNetworkSettings().getNetworks().keySet()) {
            DockerContainerNetwork network = container.getNetworkSettings().getNetworks().get(networkName);
            CreateEndpointConfig endpoint = new CreateEndpointConfig();
            endpoint.setIPAddress(network.getIPAddress());
            endpoint.setMacAddress(network.getMacAddress());
            endpoint.setNetworkID(network.getNetworkID());
            endpoints.put(networkName, endpoint);
        }
        networkConfig.setEndpointsConfig(endpoints);
        request.setNetworkingConfig(networkConfig);
        
        return request;
    }
    
    public static RecreatedContainerData fromCreatedContainerData(CreatedContainerData data) {
        RecreatedContainerData recreatedData = new RecreatedContainerData();
        recreatedData.setId(data.getId());
        recreatedData.setWarnings(data.getWarnings());
        return recreatedData;
    }
    
}
