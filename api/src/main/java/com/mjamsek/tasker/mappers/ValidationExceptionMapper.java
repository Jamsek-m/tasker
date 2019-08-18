package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.lib.v1.exceptions.ValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    
    @Override
    public Response toResponse(ValidationException e) {
        return Response.status(e.getResponse().getStatus()).entity(e.getResponse()).build();
    }
}
