package com.mjamsek.tasker.services.impl;

import com.mjamsek.tasker.apis.docker.DockerAPI;
import com.mjamsek.tasker.entities.docker.DockerContainerInfo;
import com.mjamsek.tasker.entities.docker.DockerCreateContainer;
import com.mjamsek.tasker.entities.exceptions.DockerException;
import com.mjamsek.tasker.entities.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.entities.exceptions.TaskerException;
import com.mjamsek.tasker.entities.persistence.service.DockerDaemon;
import com.mjamsek.tasker.mappers.DockerMapper;
import com.mjamsek.tasker.services.DockerDaemonService;
import com.mjamsek.tasker.services.DockerService;
import com.mjamsek.tasker.utils.DateUtil;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileWriter;
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
            } catch (WebApplicationException e) {
                throw new DockerException("Docker action failed!");
            }
        }
        throw new EntityNotFoundException("Docker daemon not found!");
    }
    
    @Override
    public String getRawContainerInfo(String containerId, DockerDaemon daemon) {
        try {
            URI daemonUri = new URI(daemon.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(daemonUri).build(DockerAPI.class);
            
            return dockerAPI.getRawContainerInfo(containerId);
        } catch (URISyntaxException e) {
            throw new TaskerException("Invalid daemon url!");
        } catch (WebApplicationException e) {
            if (e.getResponse().getStatus() == 404) {
                throw new EntityNotFoundException("Container with given id does not exists!");
            } else {
                throw new DockerException("Docker action failed!");
            }
        }
    }
    
    @Override
    public DockerContainerInfo getContainerInfo(String containerId, DockerDaemon daemon) {
        try {
            URI daemonUri = new URI(daemon.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(daemonUri).build(DockerAPI.class);
            
            return dockerAPI.getContainerInfo(containerId);
        } catch (URISyntaxException e) {
            throw new TaskerException("Invalid daemon url!");
        } catch (WebApplicationException e) {
            if (e.getResponse().getStatus() == 404) {
                throw new EntityNotFoundException("Container with given id does not exists!");
            } else {
                throw new DockerException("Docker action failed!");
            }
        }
    }
    
    @Override
    public void startContainer(String containerId, DockerDaemon daemon) {
        try {
            URI daemonUri = new URI(daemon.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(daemonUri).build(DockerAPI.class);
            dockerAPI.startContainer(containerId);
        } catch (URISyntaxException e) {
            throw new TaskerException("Invalid daemon url!");
        } catch (WebApplicationException e) {
            Response response = e.getResponse();
            if (response.getStatus() == 404) {
                throw new EntityNotFoundException("Container with given id does not exist!");
            } else {
                throw new DockerException("Docker action failed!");
            }
        }
    }
    
    @Override
    public void stopContainer(String containerId, DockerDaemon daemon) {
        try {
            URI daemonUri = new URI(daemon.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(daemonUri).build(DockerAPI.class);
            dockerAPI.stopContainer(containerId);
        } catch (URISyntaxException e) {
            throw new TaskerException("Invalid daemon url!");
        } catch (WebApplicationException e) {
            Response response = e.getResponse();
            if (response.getStatus() == 404) {
                throw new EntityNotFoundException("Container with given id does not exist!");
            } else {
                throw new DockerException("Docker action failed!");
            }
        }
    }
    
    @Override
    public void deleteContainer(String containerId, DockerDaemon daemon) {
        try {
            URI daemonUri = new URI(daemon.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(daemonUri).build(DockerAPI.class);
            dockerAPI.deleteContainer(containerId, true);
        } catch (URISyntaxException e) {
            throw new TaskerException("Invalid daemon url!");
        } catch (WebApplicationException e) {
            Response response = e.getResponse();
            if (response.getStatus() == 404) {
                throw new EntityNotFoundException("Container with given id does not exist!");
            } else {
                throw new DockerException("Docker action failed!");
            }
        }
    }
    
    @Override
    public String createContainer(String containerName, DockerDaemon daemon, DockerCreateContainer data) {
        try {
            URI daemonUri = new URI(daemon.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(daemonUri).build(DockerAPI.class);
            DockerCreateContainer.Response resp = dockerAPI.createContainer(containerName, data);
            return resp.id;
        } catch (URISyntaxException e) {
            throw new TaskerException("Invalid daemon url!");
        } catch (WebApplicationException e) {
            throw new DockerException("Docker action failed!");
        }
    }
    
    @Override
    public String recreateContainer(String containerId, DockerDaemon daemon) {
        DockerContainerInfo containerInfo = getContainerInfo(containerId, daemon);
        this.createBackupFile(containerId, daemon, containerInfo.name);
        
        stopContainer(containerId, daemon);
        deleteContainer(containerId, daemon);
        
        DockerCreateContainer createRequest = DockerMapper.fromInfoToCreate(containerInfo);
        
        if (containerInfo.name.startsWith("/")) {
            containerInfo.name = containerInfo.name.substring(1);
        }
        
        String newId = createContainer(containerInfo.name, daemon, createRequest);
        startContainer(newId, daemon);
        return newId;
    }
    
    private void createBackupFile(String containerId, DockerDaemon daemon, String containerName) {
        String backupData = getRawContainerInfo(containerId, daemon);
        
        if (containerName.startsWith("/")) {
            containerName = containerName.substring(1);
        }
        
        try {
            String fileName = DateUtil.formatDate("dd-MM-yyyy_HH-mm-ss-S") + "_" +
                containerName + "_" + containerId + "_backup.json";
            File backupFile = new File("./data/" + fileName);
            FileWriter fileWriter = new FileWriter(backupFile);
            fileWriter.write(backupData);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DockerException("Failed to backup container data, aborting recreation.");
        }
    }
    
}
