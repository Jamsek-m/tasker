package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.auth.keycloak.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.keycloak.annotations.ClientRolesAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import com.mjamsek.tasker.entities.dto.ServiceToken;
import com.mjamsek.tasker.lib.v1.Service;
import com.mjamsek.tasker.lib.v1.common.Auth;
import com.mjamsek.tasker.lib.v1.common.HttpHeader;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerContainerInfo;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerState;
import com.mjamsek.tasker.services.ServicesService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/services")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecureResource
public class ServicesResource {
    
    @Inject
    private ServicesService servicesService;
    
    @Context
    protected UriInfo uriInfo;
    
    @GET
    @AuthenticatedAllowed
    public Response getServices() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Service> services = servicesService.getServices(query);
        long servicesCount = servicesService.getServicesCount(query);
        return Response.ok(services).header(HttpHeader.X_TOTAL_COUNT, servicesCount).build();
    }
    
    @GET
    @Path("/{serviceId}")
    @AuthenticatedAllowed
    public Response getService(@PathParam("serviceId") String serviceIdOrName) {
        Service service = servicesService.getServiceByIdOrName(serviceIdOrName);
        return Response.ok(service).build();
    }
    
    @PUT
    @Path("/{serviceId}")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.DEVELOPER_ROLE})
    public Response updateService(@PathParam("serviceId") String serviceId, Service service) {
        Service updatedService = servicesService.updateService(service, serviceId);
        return Response.ok(updatedService).build();
    }
    
    @GET
    @Path("/{serviceId}/container")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.DEVELOPER_ROLE})
    public Response getContainerInfo(@PathParam("serviceId") String serviceId,
                                     @QueryParam("raw") @DefaultValue("false") boolean raw) {
        if (raw) {
            String containerInfo = servicesService.getRawServiceContainer(serviceId);
            return Response.ok(containerInfo).build();
        } else {
            DockerContainerInfo containerInfo = servicesService.getServiceContainer(serviceId);
            return Response.ok(containerInfo).build();
        }
    }
    
    @GET
    @Path("/{serviceId}/container/state")
    @AuthenticatedAllowed
    public Response getContainerStatus(@PathParam("serviceId") String serviceId) {
        DockerState state = servicesService.getContainerState(serviceId);
        return Response.ok(state).build();
    }
    
    @POST
    @Path("/{serviceId}/container/start")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.DEVELOPER_ROLE})
    public Response startContainer(@PathParam("serviceId") String serviceId) {
        servicesService.startContainer(serviceId);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{serviceId}/container/stop")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.DEVELOPER_ROLE})
    public Response stopContainer(@PathParam("serviceId") String serviceId) {
        servicesService.stopContainer(serviceId);
        return Response.ok().build();
    }
    
    @PUT
    @Path("/{serviceId}/container/recreate")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.DEVELOPER_ROLE})
    public Response recreateContainer(@PathParam("serviceId") String serviceId) {
        servicesService.recreateContainer(serviceId);
        return Response.ok().build();
    }
    
    @POST
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.DEVELOPER_ROLE})
    public Response createService(Service service) {
        Service created = servicesService.createService(service);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
    
    @GET
    @Path("/{serviceId}/health")
    @AuthenticatedAllowed
    public Response getServiceHealth(@PathParam("serviceId") String serviceId) {
        servicesService.doHealthCheck(serviceId);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{serviceId}")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.DEVELOPER_ROLE})
    public Response deleteService(@PathParam("serviceId") String serviceId) {
        servicesService.deleteService(serviceId);
        return Response.noContent().build();
    }
    
    @POST
    @Path("/{serviceId}/token")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.ADMIN_ROLE})
    public Response createToken(@PathParam("serviceId") String serviceId) {
        ServiceToken token = servicesService.generateServiceToken(serviceId);
        return Response.ok(token).build();
    }
    
}
