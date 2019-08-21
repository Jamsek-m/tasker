package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.security.annotations.Secure;
import com.mjamsek.tasker.lib.v1.DockerEndpoint;
import com.mjamsek.tasker.lib.v1.common.AuthRole;
import com.mjamsek.tasker.lib.v1.common.HttpHeader;
import com.mjamsek.tasker.services.DockerEndpointService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/docker-endpoints")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secure
public class DockerEndpointResource {
    
    @Context
    protected UriInfo uriInfo;
    
    @Inject
    private DockerEndpointService dockerEndpointService;
    
    @GET
    @PermitAll
    public Response getEndpoints() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<DockerEndpoint> endpoints = dockerEndpointService.getEndpoints(query);
        long endpointsCount = dockerEndpointService.getEndpointsCount(query);
        return Response.ok(endpoints).header(HttpHeader.X_TOTAL_COUNT, endpointsCount).build();
    }
    
    @GET
    @Path("/{endpointId}")
    @PermitAll
    public Response getEndpoint(@PathParam("endpointId") String endpointId) {
        return Response.ok(dockerEndpointService.getDockerEndpointByName(endpointId)).build();
    }
    
    @POST
    @RolesAllowed({AuthRole.ADMIN})
    public Response saveEndpoint(DockerEndpoint endpoint) {
        return Response.status(Response.Status.CREATED).entity(dockerEndpointService.saveEndpoint(endpoint)).build();
    }
    
    @PUT
    @Path("/{endpointId}")
    @RolesAllowed({AuthRole.ADMIN})
    public Response updateEndpoint(@PathParam("endpointId") String endpointId, DockerEndpoint endpoint) {
        return Response.ok(dockerEndpointService.updateEndpoint(endpoint, endpointId)).build();
    }
    
    @DELETE
    @Path("/{endpointId}")
    @RolesAllowed({AuthRole.ADMIN})
    public Response deleteEndpoint(@PathParam("endpointId") String endpointId) {
        dockerEndpointService.deleteEndpoint(endpointId);
        return Response.noContent().build();
    }
}
