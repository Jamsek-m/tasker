package com.mjamsek.tasker.plugins.docker.actions;

import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.action.ActionExecutionException;
import com.mjamsek.tasker.lib.action.ActionValidationException;
import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;
import com.mjamsek.tasker.plugins.docker.models.create.CreateContainerRequest;
import com.mjamsek.tasker.plugins.docker.models.create.CreatedContainerData;
import com.mjamsek.tasker.plugins.docker.services.DockerService;

import java.util.HashMap;

public class CreateContainerAction implements Action {
    
    @Override
    public ActionResponse executeAction(ActionInstruction actionInstruction) throws ActionExecutionException {
        DockerService dockerService = DockerService.newInstance(actionInstruction);
        if (actionInstruction.getParameters().containsKey("containerData")) {
            if (actionInstruction.getParameters().containsKey("containerName")) {
                String containerName = (String) actionInstruction.getParameters().get("containerName");
                CreateContainerRequest request = extractRequest(actionInstruction.getParameters());
                
                CreatedContainerData createdContainer = dockerService.createContainer(containerName, request);
                return new ActionResponse(actionInstruction.getActionName(), "SUCCESS!").withBody(createdContainer);
            }
            throw new ActionValidationException("Missing containerName!", actionInstruction.getActionName());
        }
        throw new ActionValidationException("Missing containerData!", actionInstruction.getActionName());
    }
    
    private CreateContainerRequest extractRequest(HashMap<String, Object> params) {
        try {
            return (CreateContainerRequest) params.get("containerData");
        } catch (ClassCastException exc) {
            throw new ActionValidationException("invalid body of request!", "DockerPlugin.create");
        }
    }
}
