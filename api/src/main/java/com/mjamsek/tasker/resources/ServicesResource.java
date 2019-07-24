package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.auth.SecureResource;
import com.mjamsek.tasker.auth.SecureResourceOrToken;
import com.mjamsek.tasker.entities.docker.DockerContainerInfo;
import com.mjamsek.tasker.entities.docker.DockerState;
import com.mjamsek.tasker.entities.dto.ServiceRequest;
import com.mjamsek.tasker.entities.dto.ServiceToken;
import com.mjamsek.tasker.entities.persistence.service.Service;
import com.mjamsek.tasker.http.HttpHeader;
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
public class ServicesResource {
    
    @Inject
    private ServicesService servicesService;
    
    @Context
    protected UriInfo uriInfo;
    
    @GET
    @SecureResource
    public Response getServices() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Service> services = servicesService.getServices(query);
        long servicesCount = servicesService.getServicesCount(query);
        return Response.ok(services).header(HttpHeader.X_TOTAL_COUNT, servicesCount).build();
    }
    
    @GET
    @Path("/{serviceId}")
    @SecureResource
    public Response getService(@PathParam("serviceId") String serviceIdOrName) {
        Service service = servicesService.getServiceByIdOrName(serviceIdOrName);
        return Response.ok(service).build();
    }
    
    @PUT
    @Path("/{serviceId}")
    @SecureResource
    public Response updateService(@PathParam("serviceId") long serviceId, ServiceRequest service) {
        Service updatedService = servicesService.updateService(service, serviceId);
        return Response.ok(updatedService).build();
    }
    
    @GET
    @Path("/{serviceId}/container")
    @SecureResourceOrToken
    public Response getContainerInfo(@PathParam("serviceId") long serviceId,
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
    @SecureResourceOrToken
    public Response getContainerStatus(@PathParam("serviceId") long serviceId) {
        DockerState state = servicesService.getContainerState(serviceId);
        return Response.ok(state).build();
    }
    
    @POST
    @Path("/{serviceId}/container/start")
    @SecureResourceOrToken
    public Response startContainer(@PathParam("serviceId") long serviceId) {
        servicesService.startContainer(serviceId);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{serviceId}/container/stop")
    @SecureResourceOrToken
    public Response stopContainer(@PathParam("serviceId") long serviceId) {
        servicesService.stopContainer(serviceId);
        return Response.ok().build();
    }
    
    @PUT
    @Path("/{serviceId}/container/recreate")
    @SecureResourceOrToken
    public Response recreateContainer(@PathParam("serviceId") long serviceId) {
        servicesService.recreateContainer(serviceId);
        return Response.ok().build();
    }
    
    @POST
    @SecureResource
    public Response createService(ServiceRequest service) {
        Service created = servicesService.createService(service);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
    
    @GET
    @Path("/{serviceId}/health")
    @SecureResourceOrToken
    public Response getServiceHealth(@PathParam("serviceId") long serviceId) {
        servicesService.doHealthCheck(serviceId);
        return Response.ok().build();
    }
    
    @PATCH
    @Path("/{serviceId}/token")
    @SecureResource
    public Response generateServiceToken(@PathParam("serviceId") long serviceId) {
        ServiceToken token = servicesService.generateServiceToken(serviceId);
        return Response.status(Response.Status.CREATED).entity(token).build();
    }
    
    @DELETE
    @Path("/{serviceId}")
    @SecureResource
    public Response deleteService(@PathParam("serviceId") long serviceId) {
        servicesService.deleteService(serviceId);
        return Response.noContent().build();
    }
    
}
