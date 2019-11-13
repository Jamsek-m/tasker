package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.auth.keycloak.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.keycloak.annotations.ClientRolesAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import com.mjamsek.tasker.lib.v1.Server;
import com.mjamsek.tasker.lib.v1.common.Auth;
import com.mjamsek.tasker.lib.v1.common.HttpHeader;
import com.mjamsek.tasker.services.ServerService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/servers")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecureResource
public class ServerResource {
    
    @Inject
    private ServerService serverService;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    @AuthenticatedAllowed
    public Response getServers() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Server> servers = serverService.getServers(query);
        long serversCount = serverService.getServersCount(query);
        return Response.ok(servers).header(HttpHeader.X_TOTAL_COUNT, serversCount).build();
    }
    
    @GET
    @Path("/{serverId}")
    @AuthenticatedAllowed
    public Response getServer(@PathParam("serverId") String serverId) {
        return Response.ok(serverService.getServer(serverId)).build();
    }
    
    @POST
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.ADMIN_ROLE})
    public Response createServer(Server server) {
        return Response.status(Response.Status.CREATED).entity(serverService.createServer(server)).build();
    }
    
    @PATCH
    @Path("/{serverId}")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.ADMIN_ROLE})
    public Response updateServer(@PathParam("serverId") String serverId, Server server) {
        return Response.ok(serverService.updateServer(server, serverId)).build();
    }
    
    @DELETE
    @Path("/{serverId}")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.ADMIN_ROLE})
    public Response deleteServer(@PathParam("serverId") String serverId) {
        serverService.deleteServer(serverId);
        return Response.noContent().build();
    }
    
}
