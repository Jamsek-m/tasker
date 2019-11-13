package com.mjamsek.tasker.resources;

import com.mjamsek.auth.keycloak.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import com.mjamsek.tasker.lib.v1.Statistics;
import com.mjamsek.tasker.services.StatisticsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dashboard")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecureResource
public class DashboardResource {
    
    @Inject
    private StatisticsService statisticsService;
    
    @GET
    @Path("/statistics")
    @AuthenticatedAllowed
    public Response getStatistics() {
        Statistics statistics = statisticsService.getStatistics();
        return Response.ok(statistics).build();
    }
    
}
