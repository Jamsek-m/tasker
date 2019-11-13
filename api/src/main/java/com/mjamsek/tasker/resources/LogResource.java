package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.auth.keycloak.annotations.ClientRolesAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import com.mjamsek.tasker.lib.v1.LogEntry;
import com.mjamsek.tasker.lib.v1.common.Auth;
import com.mjamsek.tasker.lib.v1.common.HttpHeader;
import com.mjamsek.tasker.services.LogService;

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
@SecureResource
public class LogResource {
    
    @Inject
    private LogService logService;
    
    @Context
    protected UriInfo uriInfo;
    
    @GET
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.ADMIN_ROLE})
    public Response getLogs() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<LogEntry> logs = logService.getLogs(query);
        long logsCount = logService.getLogsCount(query);
        return Response.ok(logs).header(HttpHeader.X_TOTAL_COUNT, logsCount).build();
    }
}
