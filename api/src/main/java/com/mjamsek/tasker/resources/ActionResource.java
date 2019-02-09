package com.mjamsek.tasker.resources;

import com.mjamsek.tasker.admin.auth.SecureResource;
import com.mjamsek.tasker.admin.entities.LogSeverity;
import com.mjamsek.tasker.admin.services.LogService;
import com.mjamsek.tasker.admin.services.TokenService;
import com.mjamsek.tasker.delegator.ActionsService;
import com.mjamsek.tasker.delegator.Delegator;
import com.mjamsek.tasker.http.HttpHeader;
import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/actions")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActionResource {
    
    @Inject
    private Delegator delegator;
    
    @Inject
    private ActionsService actionsService;
    
    @Inject
    private TokenService tokenService;
    
    @Inject
    private LogService logService;
    
    @GET
    public Response getAllAvailableActions() {
        return Response.ok(actionsService.getAllActions()).build();
    }
    
    @POST
    @Path("/execute")
    public Response executeAction(
        @HeaderParam(HttpHeader.X_TASKER_KEY) String secretKey,
        @HeaderParam(HttpHeader.X_TASKER_NAME) String tokenName,
        ActionInstruction actionInstruction
    ) {
        if (tokenService.validateToken(secretKey, tokenName, actionInstruction.getActionName())) {
            ActionResponse response = delegator.delegateAction(actionInstruction);
            logService.log(LogSeverity.INFO, "Action '" + actionInstruction.getActionName() + "' was invoked successfully using token '" + tokenName + "'.");
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
}
