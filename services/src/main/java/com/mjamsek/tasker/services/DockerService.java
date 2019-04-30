package com.mjamsek.tasker.services;

import com.mjamsek.tasker.entities.docker.DockerContainerInfo;

import java.util.List;

public interface DockerService {
    
    List<DockerContainerInfo> queryContainersByName(String name, long daemonId);

}
