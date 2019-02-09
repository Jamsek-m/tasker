package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.admin.auth.SecureResource;
import com.mjamsek.tasker.admin.entities.LogEntry;
import com.mjamsek.tasker.admin.services.LogService;
import com.mjamsek.tasker.http.HttpHeader;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/logs")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogResource {
    
    @Inject
    private LogService logService;
    
    @Context
    protected UriInfo uriInfo;
    
    @GET
    @SecureResource
    public Response getLogs() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<LogEntry> logs = logService.getLogs(query);
        long logsCount = logService.getLogsCount(query);
        return Response.ok(logs).header(HttpHeader.X_TOTAL_COUNT, logsCount).build();
    }
}
