package com.mjamsek.tasker.plugins.docker.services;

import com.mjamsek.tasker.plugins.docker.models.create.CreateContainerRequest;
import com.mjamsek.tasker.plugins.docker.models.details.DockerContainer;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient
@Dependent
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DockerAPI {
    
    @GET
    @Path("/containers/json")
    JsonArray getContainersByName(@QueryParam("filters") String filterJson);
    
    @GET
    @Path("/containers/{id}/json")
    DockerContainer getContainerInfo(@PathParam("id") String containerId);
    
    @POST
    @Path("/containers/{id}/stop")
    Response stopContainer(@PathParam("id") String containerId);
    
    @POST
    @Path("/containers/{id}/start")
    Response startContainer(@PathParam("id") String containerId);
    
    @DELETE
    @Path("/containers/{id}")
    Response deleteContainer(@PathParam("id") String containerId);
    
    @POST
    @Path("/containers/create")
    Response createContainer(@QueryParam("name") String containerName, CreateContainerRequest container);
}
