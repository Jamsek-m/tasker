package com.mjamsek.tasker.plugins.docker.services;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.mjamsek.tasker.lib.action.ActionExecutionException;
import com.mjamsek.tasker.lib.action.ActionValidationException;
import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;
import com.mjamsek.tasker.plugins.docker.models.config.ConfigEntry;
import com.mjamsek.tasker.plugins.docker.models.create.CreateContainerRequest;
import com.mjamsek.tasker.plugins.docker.models.create.CreatedContainerData;
import com.mjamsek.tasker.plugins.docker.models.details.DockerContainer;
import com.mjamsek.tasker.plugins.docker.models.list.DockerContainerCard;
import com.mjamsek.tasker.plugins.docker.utils.CreateContainerMapper;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.json.JsonArray;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class DockerService {
    
    private DockerAPI dockerAPI;
    
    /**
     * Builds DockerService instance with given docker API endpoint
     * @param actionInstruction received configuration for action execution
     * @return instance of DockerService
     */
    public static DockerService newInstance(ActionInstruction actionInstruction) {
        if (actionInstruction.getParameters().containsKey("configDockerAPI")) {
            String configKey = (String) actionInstruction.getParameters().get("configDockerAPI");
    
            DockerService service = new DockerService();
            String dockerAPIUrl = getDockerAPIUrl(configKey);
            if (dockerAPIUrl == null) {
                throw new ActionExecutionException("Error initializing docker api url!", "#init");
            }
            try {
                service.dockerAPI = RestClientBuilder.newBuilder()
                    .baseUrl(new URL(dockerAPIUrl))
                    .build(DockerAPI.class);
            } catch (MalformedURLException e) {
                throw new ActionExecutionException("Error initializing docker api url! " + e.getMessage(), "#init");
            }
            return service;
        } else {
            throw new ActionValidationException("Missing configDockerAPI!", actionInstruction.getActionName());
        }
    }
    
    private static String getDockerAPIUrl(String configKey) {
        try {
            String baseUrl = ConfigurationUtil.getInstance().get("kumuluzee.server.base-url").orElse("");
            URL serverUrl = new URL(baseUrl + "/v1");
            ConfigService configService = RestClientBuilder.newBuilder()
                .baseUrl(serverUrl)
                .build(ConfigService.class);
            ConfigEntry entry = configService.getConfig(configKey);
            return entry.getValue();
        } catch (MalformedURLException e) {
            return null;
        }
    }
    
    public String getContainerId(String containerName) {
        try {
            String filterJson = URLEncoder.encode(String.format("{\"name\":[\"%s\"]}", containerName), "UTF-8");
            JsonArray containerHits = dockerAPI.getContainersByName(filterJson);
            if (containerHits.size() > 0) {
                DockerContainerCard containerCard = DockerContainerCard.fromJsonObject(containerHits.getJsonObject(0));
                return containerCard.getId();
            } else {
                throw new ActionExecutionException("Wrong containerName! Container not found!", "DockerPlugin.getId");
            }
        } catch (UnsupportedEncodingException e) {
            throw new ActionExecutionException("Unsupported encoding for filter json!", "DockerPlugin.getId");
        }
    }
    
    public DockerContainer getContainerInfoByName(String containerName) {
        String containerId = getContainerId(containerName);
        return getContainerInfo(containerId);
    }
    
    public DockerContainer getContainerInfo(String containerId) {
        return dockerAPI.getContainerInfo(containerId);
    }
    
    public void startContainer(String containerId) {
        Response response;
        try {
            response = dockerAPI.startContainer(containerId);
        } catch (WebApplicationException e) {
            response = e.getResponse();
        }
        if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            response.close();
            throw new ActionExecutionException("Error when starting container!", "DockerPlugin.start");
        }
    }
    
    public void stopContainer(String containerId) {
        Response response;
        try {
            response = dockerAPI.stopContainer(containerId);
        } catch (WebApplicationException e) {
            response = e.getResponse();
        }
        if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            response.close();
            throw new ActionExecutionException("Error when stopping container!", "DockerPlugin.stop");
        }
    }
    
    public void deleteContainer(String containerId) {
        Response response;
        try {
            response = dockerAPI.deleteContainer(containerId);
        } catch (WebApplicationException e) {
            response = e.getResponse();
        }
        
        if (response.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
            response.close();
            stopContainer(containerId);
            deleteContainer(containerId);
        } else if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            response.close();
            throw new ActionExecutionException("Error when deleting container!", "DockerPlugin.delete");
        }
    }
    
    public CreatedContainerData createContainer(String containerName, CreateContainerRequest request) {
        Response response;
        try {
            response = dockerAPI.createContainer(containerName, request);
        } catch (WebApplicationException e) {
            response = e.getResponse();
        }
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            response.close();
            throw new ActionExecutionException("Error when creating container!", "DockerPlugin.create");
        }
        return response.readEntity(CreatedContainerData.class);
    }
    
    public CreatedContainerData recreateContainer(String containerName) {
        String containerId = getContainerId(containerName);
        DockerContainer containerInfo = getContainerInfo(containerId);
        CreateContainerRequest request = CreateContainerMapper.fromContainerInfo(containerInfo);
        String createdContainerName = extractContainerName(containerInfo);
        deleteContainer(containerId);
        CreatedContainerData createdContainer = createContainer(createdContainerName, request);
        startContainer(createdContainer.getId());
        return createdContainer;
    }
    
    private String extractContainerName(DockerContainer container) {
        String containerName = container.getName();
        if (containerName.startsWith("/")) {
            containerName = containerName.substring(1);
        }
        return containerName;
    }
}
