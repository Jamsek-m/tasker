package com.mjamsek.tasker.apis.docker;

import com.mjamsek.tasker.lib.v1.integration.docker.DockerContainerInfo;
import com.mjamsek.tasker.lib.v1.integration.docker.DockerCreateContainer;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.annotation.RegisterProviders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@RegisterRestClient
@Dependent
@RegisterProviders({
    @RegisterProvider(DockerAPIExceptionMapper.class),
    @RegisterProvider(DockerAPINoResponseExceptionMapper.class)
})
public interface DockerAPI {
    
    @GET
    @Path("/info")
    Response checkAvailability();
    
    @GET
    @Path("/containers/json")
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
    
    @POST
    @Path("/containers/{containerId}/start")
    Response startContainer(@PathParam("containerId") String containerId);
    
    @POST
    @Path("/containers/{containerId}/stop")
    Response stopContainer(@PathParam("containerId") String containerId);
    
    @POST
    @Path("/containers/create")
    DockerCreateContainer.Response createContainer(@QueryParam("name") String name, DockerCreateContainer dto);
    
    @DELETE
    @Path("/containers/{containerId}")
    Response deleteContainer(
        @PathParam("containerId") String containerId,
        @QueryParam("force") boolean force
    );
}
