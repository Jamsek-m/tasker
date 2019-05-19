package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.entities.persistence.service.DockerDaemon;
import com.mjamsek.tasker.http.HttpHeader;
import com.mjamsek.tasker.services.DockerDaemonService;
import com.mjamsek.tasker.services.LogService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/docker-daemons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DockerDaemonResource {
    
    @Context
    protected UriInfo uriInfo;
    
    @Inject
    private DockerDaemonService dockerDaemonService;
    
    @GET
    public Response getDaemons() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<DockerDaemon> daemons = dockerDaemonService.getDaemons(query);
        long daemonsCount = dockerDaemonService.getDaemonsCount(query);
        return Response.ok(daemons).header(HttpHeader.X_TOTAL_COUNT, daemonsCount).build();
    }
    
    @GET
    @Path("/{daemonId}")
    public Response getDaemon(@PathParam("daemonId") long daemonId) {
        return Response.ok(dockerDaemonService.getDaemon(daemonId)).build();
    }
    
    @POST
    public Response saveDaemon(DockerDaemon daemon) {
        return Response.status(Response.Status.CREATED).entity(dockerDaemonService.saveDaemon(daemon)).build();
    }
    
    @PUT
    @Path("/{daemonId}")
    public Response updateDaemon(@PathParam("daemonId") long daemonId, DockerDaemon daemon) {
        return Response.ok(dockerDaemonService.updateDaemon(daemon, daemonId)).build();
    }
    
    @DELETE
    @Path("/{daemonId}")
    public Response get(@PathParam("daemonId") long daemonId) {
        dockerDaemonService.deleteDaemon(daemonId);
        return Response.noContent().build();
    }
}
