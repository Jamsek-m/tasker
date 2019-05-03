package com.mjamsek.tasker.apis;

import com.mjamsek.tasker.entities.docker.DockerContainerInfo;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;

@RegisterRestClient
@Dependent
public interface DockerAPI {
    
    @Path("/containers/json")
    @GET
    List<DockerContainerInfo> queryContainersByName(
        @QueryParam("filters") String filters,
        @QueryParam("all") boolean all
    );
    
    @GET
    @Path("/containers/{containerId}/json")
    String getRawContainerInfo(@PathParam("containerId") String containerId);
    
    @GET
    @Path("/containers/{containerId}/json")
    DockerContainerInfo getContainerInfo(@PathParam("containerId") String containerId);
    
}
