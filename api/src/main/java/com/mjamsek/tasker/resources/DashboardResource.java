package com.mjamsek.tasker.resources;

import com.kumuluz.ee.security.annotations.Secure;
import com.mjamsek.tasker.lib.v1.Statistics;
import com.mjamsek.tasker.services.StatisticsService;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dashboard")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secure
public class DashboardResource {
    
    @Inject
    private StatisticsService statisticsService;
    
    @GET
    @Path("/statistics")
    @PermitAll
    public Response getStatistics() {
        Statistics statistics = statisticsService.getStatistics();
        return Response.ok(statistics).build();
    }
    
}
