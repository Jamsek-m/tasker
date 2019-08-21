package com.mjamsek.tasker.apis.docker;

import com.mjamsek.tasker.lib.v1.exceptions.DockerContainerNotFoundException;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class DockerAPIExceptionMapper implements ResponseExceptionMapper<DockerContainerNotFoundException> {
    
    @Override
    public DockerContainerNotFoundException toThrowable(Response response) {
        return new DockerContainerNotFoundException("Container with given id does not exist!");
    }
    
    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return status == Response.Status.NOT_FOUND.getStatusCode();
    }
}
