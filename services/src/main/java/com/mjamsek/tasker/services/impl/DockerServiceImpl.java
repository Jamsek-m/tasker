package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.apis.docker.DockerAPI;
import com.mjamsek.tasker.lib.v1.exceptions.DockerException;
import com.mjamsek.tasker.lib.v1.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.lib.v1.exceptions.FailedHealthCheckException;
import com.mjamsek.tasker.lib.v1.exceptions.TaskerException;
import com.mjamsek.tasker.entities.persistence.service.DockerEndpointEntity;
import com.mjamsek.tasker.lib.v1.DockerEndpoint;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerContainerInfo;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerCreateContainer;
import com.mjamsek.tasker.mappers.DockerMapper;
import com.mjamsek.tasker.services.DockerEndpointService;
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
    private DockerEndpointService dockerEndpointService;
    
    @Override
    public void checkEndpointAvailability() {
        List<DockerEndpoint> endpoints = dockerEndpointService.getEndpoints(new QueryParameters());
        
        endpoints.forEach(endpoint -> {
            try {
                URI endpointUri = new URI(endpoint.getUrl());
                DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(endpointUri).build(DockerAPI.class);
                Response response = dockerAPI.checkAvailability();
                if (response.getStatus() >= 400) {
                    throw new FailedHealthCheckException(endpoint.getName());
                }
            } catch (URISyntaxException e) {
                throw new TaskerException("Invalid daemon url!");
            } catch (WebApplicationException e) {
                if (e.getResponse().getStatus() == 404) {
                    throw new FailedHealthCheckException(endpoint.getName());
                } else {
                    throw new DockerException("Failed to connect to daemon!");
                }
            }
        });
    }
    
    @Override
    public List<DockerContainerInfo> queryContainersByName(String name, String endpointId) {
        DockerEndpointEntity dockerEndpoint = dockerEndpointService.getDockerEndpoint(endpointId);
        
        if (name.isEmpty()) {
            return new ArrayList<>();
        }
        
        if (dockerEndpoint != null) {
            try {
                URI daemonUri = new URI(dockerEndpoint.getUrl());
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
    public String getRawContainerInfo(String containerId, DockerEndpointEntity endpoint) {
        try {
            URI endpointUri = new URI(endpoint.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(endpointUri).build(DockerAPI.class);
            
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
    public DockerContainerInfo getContainerInfo(String containerId, DockerEndpointEntity endpoint) {
        try {
            URI endpointUri = new URI(endpoint.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(endpointUri).build(DockerAPI.class);
            
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
    public void startContainer(String containerId, DockerEndpointEntity endpoint) {
        try {
            URI endpointUri = new URI(endpoint.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(endpointUri).build(DockerAPI.class);
            
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
    public void stopContainer(String containerId, DockerEndpointEntity endpoint) {
        try {
            URI endpointUri = new URI(endpoint.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(endpointUri).build(DockerAPI.class);
            
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
    public void deleteContainer(String containerId, DockerEndpointEntity endpoint) {
        try {
            URI endpointUri = new URI(endpoint.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(endpointUri).build(DockerAPI.class);
            
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
    public String createContainer(String containerName, DockerEndpointEntity endpoint, DockerCreateContainer data) {
        try {
            URI endpointUri = new URI(endpoint.getUrl());
            DockerAPI dockerAPI = RestClientBuilder.newBuilder().baseUri(endpointUri).build(DockerAPI.class);
            DockerCreateContainer.Response resp = dockerAPI.createContainer(containerName, data);
            return resp.id;
        } catch (URISyntaxException e) {
            throw new TaskerException("Invalid daemon url!");
        } catch (WebApplicationException e) {
            throw new DockerException("Docker action failed!");
        }
    }
    
    @Override
    public String recreateContainer(String containerId, DockerEndpointEntity endpoint) {
        DockerContainerInfo containerInfo = getContainerInfo(containerId, endpoint);
        this.createBackupFile(containerId, endpoint, containerInfo.name);
        
        stopContainer(containerId, endpoint);
        deleteContainer(containerId, endpoint);
        
        DockerCreateContainer createRequest = DockerMapper.fromInfoToCreate(containerInfo);
        
        if (containerInfo.name.startsWith("/")) {
            containerInfo.name = containerInfo.name.substring(1);
        }
        
        String newId = createContainer(containerInfo.name, endpoint, createRequest);
        startContainer(newId, endpoint);
        return newId;
    }
    
    private void createBackupFile(String containerId, DockerEndpointEntity endpoint, String containerName) {
        String backupData = getRawContainerInfo(containerId, endpoint);
        
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
