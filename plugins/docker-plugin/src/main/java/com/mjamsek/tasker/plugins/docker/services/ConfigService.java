package com.mjamsek.tasker.plugins.docker.services;

import com.mjamsek.tasker.plugins.docker.models.config.ConfigEntry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
@Dependent
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ConfigService {
    
    @GET
    @Path("/config/{key}")
    ConfigEntry getConfig(@PathParam("key") String key);
}
