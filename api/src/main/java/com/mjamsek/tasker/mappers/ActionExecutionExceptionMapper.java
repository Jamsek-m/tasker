package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.admin.entities.LogSeverity;
import com.mjamsek.tasker.admin.services.LogService;
import com.mjamsek.tasker.lib.action.ActionExecutionException;
import com.mjamsek.tasker.lib.models.ActionResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@RequestScoped
public class ActionExecutionExceptionMapper implements ExceptionMapper<ActionExecutionException> {
    
    @Inject
    private LogService logService;
    
    @Override
    public Response toResponse(ActionExecutionException exception) {
        ActionResponse response = new ActionResponse(exception.getActionName(), "FAILED!");
        
        ExceptionData data = new ExceptionData();
        data.message = exception.getMessage();
        response.setActionResponse(data);
    
        logService.log(LogSeverity.ERROR, "Error invoking action '" + exception.getActionName() + "'. Error: " + exception.getMessage());
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(response).build();
    }
    
    public static class ExceptionData {
        public String message;
    }
}
