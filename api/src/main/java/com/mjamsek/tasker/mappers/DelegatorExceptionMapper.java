package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.delegator.exceptions.DelegatorException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DelegatorExceptionMapper implements ExceptionMapper<DelegatorException> {
    
    @Override
    public Response toResponse(DelegatorException exception) {
        DelegatorExcData data = new DelegatorExcData();
        data.message = exception.getMessage();
        return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
    }
    
    public static class DelegatorExcData {
        public String message;
    }
}
