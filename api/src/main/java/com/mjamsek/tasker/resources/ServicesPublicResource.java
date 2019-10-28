package com.mjamsek.tasker.resources;

import com.mjamsek.tasker.services.ServicesPublicService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/public/services")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicesPublicResource {
    
    @Inject
    private ServicesPublicService servicesService;
    
    @POST
    @Path("/{serviceId}/container/start")
    public Response startContainer(@PathParam("serviceId") String serviceId) {
        servicesService.startContainer(serviceId);
        return Response.ok().build();
    }
    
    @POST
    @Path("/{serviceId}/container/recreate")
    public Response recreateContainer(@PathParam("serviceId") String serviceId) {
        servicesService.recreateContainer(serviceId);
        return Response.ok().build();
    }
    
}
