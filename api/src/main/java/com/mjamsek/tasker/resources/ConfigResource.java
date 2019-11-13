package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.auth.keycloak.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.keycloak.annotations.ClientRolesAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import com.mjamsek.tasker.lib.v1.ConfigEntry;
import com.mjamsek.tasker.lib.v1.common.Auth;
import com.mjamsek.tasker.lib.v1.common.HttpHeader;
import com.mjamsek.tasker.services.ConfigService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecureResource
public class ConfigResource {
    
    @Context
    protected UriInfo uriInfo;
    
    @Inject
    private ConfigService configService;
    
    @GET
    @AuthenticatedAllowed
    public Response getConfiguration() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<ConfigEntry> configEntries = configService.getConfiguration(query);
        long configEntriesCount = configService.getConfigurationCount(query);
        return Response.ok(configEntries).header(HttpHeader.X_TOTAL_COUNT, configEntriesCount).build();
    }
    
    @GET
    @Path("/{key}")
    @AuthenticatedAllowed
    public Response getConfig(@PathParam("key") String key) {
        return Response.ok().entity(configService.getConfig(key)).build();
    }
    
    @POST
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.ADMIN_ROLE})
    public Response addConfig(ConfigEntry configEntry) {
        configService.addConfiguration(configEntry);
        return Response.status(Response.Status.CREATED).build();
    }
    
    @PUT
    @Path("/{id}")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.ADMIN_ROLE})
    public Response updateConfig(@PathParam("id") String id, ConfigEntry configEntry) {
        configService.updateConfiguration(configEntry, id);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{id}")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.ADMIN_ROLE})
    public Response removeConfig(@PathParam("id") String id) {
        configService.deleteConfiguration(id);
        return Response.noContent().build();
    }
    
}
