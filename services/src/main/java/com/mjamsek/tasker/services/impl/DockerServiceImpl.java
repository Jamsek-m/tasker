package com.mjamsek.tasker.services.impl;

import com.mjamsek.tasker.apis.DockerAPI;
import com.mjamsek.tasker.entities.docker.DockerContainerInfo;
import com.mjamsek.tasker.entities.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.entities.exceptions.TaskerException;
import com.mjamsek.tasker.entities.persistence.service.DockerDaemon;
import com.mjamsek.tasker.services.DockerDaemonService;
import com.mjamsek.tasker.services.DockerService;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DockerServiceImpl implements DockerService {
    
    @Inject
    private DockerDaemonService dockerDaemonService;
    
    @Override
    public List<DockerContainerInfo> queryContainersByName(String name, long daemonId) {
        DockerDaemon daemon = dockerDaemonService.getDaemon(daemonId);
        
        if (name.isEmpty()) {
            return new ArrayList<>();
        }
        
        if (daemon != null) {
            try {
                URI daemonUri = new URI(daemon.getUrl());
                DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(daemonUri).build(DockerAPI.class);
                
                String filtersParam = "%7B\"name\":[\"" + name + "\"]%7D";
                
                return dockerAPI.queryContainersByName(filtersParam, true);
            } catch (URISyntaxException e) {
                throw new TaskerException("Invalid daemon url!");
            }
        }
        throw new EntityNotFoundException("Docker daemon not found!");
    }
}
