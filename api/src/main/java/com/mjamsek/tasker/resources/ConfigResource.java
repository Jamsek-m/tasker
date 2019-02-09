package com.mjamsek.tasker.resources;

import com.mjamsek.tasker.admin.auth.SecureResource;
import com.mjamsek.tasker.admin.entities.ConfigEntry;
import com.mjamsek.tasker.admin.services.ConfigService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigResource {
    
    @Inject
    private ConfigService configService;
    
    @GET
    @SecureResource
    public Response getConfiguration() {
        return Response.ok(configService.getConfiguration()).build();
    }
    
    @GET
    @Path("/{key}")
    @SecureResource
    public Response getConfig(@PathParam("key") String key) {
        return Response.ok().entity(configService.getConfig(key)).build();
    }
    
    @POST
    @SecureResource
    public Response addConfig(ConfigEntry configEntry) {
        configService.addConfiguration(configEntry);
        return Response.status(Response.Status.CREATED).build();
    }
    
    @PUT
    @Path("/{id}")
    @SecureResource
    public Response updateConfig(@PathParam("id") long id, ConfigEntry configEntry) {
        configService.updateConfiguration(configEntry);
        return Response.ok().build();
    }
    
}
