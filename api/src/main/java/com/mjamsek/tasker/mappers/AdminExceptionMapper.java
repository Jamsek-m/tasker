package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.entities.exceptions.*;

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
        } else if (exception instanceof EntityNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        } else if (exception instanceof MissingHealthCheckException) {
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(response).build();
        } else if (exception instanceof FailedHealthCheckException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } else if (exception instanceof ValidationException) {
            return Response.status(422).entity(response).build();
        } else if (exception instanceof ConflictException) {
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        } else if (exception instanceof NotDeployedException) {
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(response).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }
}
