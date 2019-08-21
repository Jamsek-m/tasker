package com.mjamsek.tasker.apis.docker;

import com.mjamsek.tasker.lib.v1.exceptions.DockerDaemonUnavailableException;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class DockerAPINoResponseExceptionMapper implements ResponseExceptionMapper<DockerDaemonUnavailableException> {
    
    @Override
    public DockerDaemonUnavailableException toThrowable(Response response) {
        return new DockerDaemonUnavailableException("Docker daemon is not available!");
    }
    
    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return status == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
    }
}
