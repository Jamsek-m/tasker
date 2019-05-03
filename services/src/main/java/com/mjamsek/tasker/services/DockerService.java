package com.mjamsek.tasker.services;

import com.mjamsek.tasker.entities.docker.DockerContainerInfo;
import com.mjamsek.tasker.entities.persistence.service.DockerDaemon;

import java.util.List;

public interface DockerService {
    
    List<DockerContainerInfo> queryContainersByName(String name, long daemonId);
    
    String getRawContainerInfo(String containerId, DockerDaemon daemon);
    
    DockerContainerInfo getContainerInfo(String containerId, DockerDaemon daemon);

}
