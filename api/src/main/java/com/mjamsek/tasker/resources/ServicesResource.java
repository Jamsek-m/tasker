package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
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
    public Response getServices() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Service> services = servicesService.getServices(query);
        long servicesCount = servicesService.getServicesCount(query);
        return Response.ok(services).header(HttpHeader.X_TOTAL_COUNT, servicesCount).build();
    }
    
    @GET
    @Path("/{serviceId}")
    public Response getService(@PathParam("serviceId") String serviceIdOrName) {
        Service service = servicesService.getServiceByIdOrName(serviceIdOrName);
        return Response.ok(service).build();
    }
    
    @POST
    public Response createService(ServiceRequest service) {
        Service created = servicesService.createService(service);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
    
    @GET
    @Path("/{serviceId}/health")
    public Response getService(@PathParam("serviceId") long serviceId) {
        servicesService.doHealthCheck(serviceId);
        return Response.ok().build();
    }
    
    @PATCH
    @Path("/{serviceId}/token")
    public Response generateServiceToken(@PathParam("serviceId") long serviceId) {
        ServiceToken token = servicesService.generateServiceToken(serviceId);
        return Response.status(Response.Status.CREATED).entity(token).build();
    }
    
    @DELETE
    @Path("/{serviceId}")
    public Response deleteService(@PathParam("serviceId") long serviceId) {
        servicesService.deleteService(serviceId);
        return Response.noContent().build();
    }
    
}
