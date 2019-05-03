package com.mjamsek.tasker.resources;

import com.mjamsek.tasker.entities.docker.DockerContainerInfo;
import com.mjamsek.tasker.services.DockerService;

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
public class DockerResource {

    @Inject
    private DockerService dockerService;
    
    @GET
    public Response queryContainersByName(@QueryParam("name") String name, @QueryParam("daemonId") long daemonId) {
        List<DockerContainerInfo> containers = dockerService.queryContainersByName(name, daemonId);
        return Response.ok(containers).build();
    }
}
