package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.lib.action.ActionExecutionException;
import com.mjamsek.tasker.lib.action.ActionValidationException;
import com.mjamsek.tasker.lib.models.ActionResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ActionValidationExceptionMapper implements ExceptionMapper<ActionValidationException> {
    
    @Override
    public Response toResponse(ActionValidationException exception) {
        ActionResponse response = new ActionResponse(exception.getActionName(), "FAILED!");
        
        ExceptionData data = new ExceptionData();
        data.message = exception.getMessage();
        
        response.setActionResponse(data);
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(response).build();
    }
    
    public static class ExceptionData {
        public String message;
    }
}
