package com.mjamsek.tasker.plugins.docker.actions;

import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.action.ActionExecutionException;
import com.mjamsek.tasker.lib.action.ActionValidationException;
import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;
import com.mjamsek.tasker.plugins.docker.services.DockerService;

import java.util.HashMap;

public class GetContainerIdAction implements Action {
    
    @Override
    public ActionResponse executeAction(ActionInstruction actionInstruction) throws ActionExecutionException {
        DockerService dockerService = DockerService.newInstance(actionInstruction);
        if (actionInstruction.getParameters().containsKey("containerName")) {
            String containerName = (String) actionInstruction.getParameters().get("containerName");
            String containerId = dockerService.getContainerId(containerName);
            
            HashMap<String, String> responseBody = new HashMap<>();
            responseBody.put("containerId", containerId);
            responseBody.put("queriedName", containerName);
            return new ActionResponse(actionInstruction.getActionName(), "SUCCESS").withBody(responseBody);
        } else {
            throw new ActionValidationException("Missing containerName!", actionInstruction.getActionName());
        }
    }
    
}
