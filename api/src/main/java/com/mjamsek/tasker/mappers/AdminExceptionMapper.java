package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.entities.exceptions.DockerException;
import com.mjamsek.tasker.entities.exceptions.TaskerException;
import com.mjamsek.tasker.entities.exceptions.UnauthorizedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

@Provider
public class AdminExceptionMapper implements ExceptionMapper<TaskerException> {
    
    @Override
    public Response toResponse(TaskerException exception) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());
        if (exception instanceof DockerException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        } else if (exception instanceof UnauthorizedException) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }
}
