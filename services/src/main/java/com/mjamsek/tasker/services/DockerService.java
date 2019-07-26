package com.mjamsek.tasker.services;

import com.mjamsek.tasker.entities.docker.DockerContainerInfo;
import com.mjamsek.tasker.entities.docker.DockerCreateContainer;
import com.mjamsek.tasker.entities.exceptions.FailedHealthCheckException;
import com.mjamsek.tasker.entities.persistence.service.DockerDaemon;

import java.util.List;

public interface DockerService {
    
    void checkDaemonAvailability() throws FailedHealthCheckException;
    
    List<DockerContainerInfo> queryContainersByName(String name, long daemonId);
    
    String getRawContainerInfo(String containerId, DockerDaemon daemon);
    
    DockerContainerInfo getContainerInfo(String containerId, DockerDaemon daemon);
    
    void startContainer(String containerId, DockerDaemon daemon);
    
    void stopContainer(String containerId, DockerDaemon daemon);
    
    void deleteContainer(String containerId, DockerDaemon daemon);
    
    String createContainer(String containerName, DockerDaemon daemon, DockerCreateContainer data);
    
    String recreateContainer(String containerId, DockerDaemon daemon);

}
