package com.mjamsek.tasker.resources;

import com.kumuluz.ee.security.annotations.Secure;
import com.mjamsek.tasker.lib.v1.common.HttpHeader;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerContainerInfo;
import com.mjamsek.tasker.services.DockerService;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Path("/docker")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secure
public class DockerResource {

    @Inject
    private DockerService dockerService;
    
    @GET
    @PermitAll
    public Response queryContainersByName(@QueryParam("name") String name, @QueryParam("daemonId") String endpointId) {
        List<DockerContainerInfo> containers = dockerService.queryContainersByName(name, endpointId);
        return Response.ok(containers).header(HttpHeader.X_TOTAL_COUNT, containers.size()).build();
    }
}
