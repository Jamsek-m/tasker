package com.mjamsek.tasker.plugins.docker.actions;

import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.action.ActionExecutionException;
import com.mjamsek.tasker.lib.action.ActionValidationException;
import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;
import com.mjamsek.tasker.plugins.docker.models.details.DockerContainer;
import com.mjamsek.tasker.plugins.docker.services.DockerService;

public class GetContainerInfoAction implements Action {
    
    @Override
    public ActionResponse executeAction(ActionInstruction actionInstruction) throws ActionExecutionException {
        DockerService dockerService = DockerService.newInstance(actionInstruction);
        String containerId;
        // if given id, it has priority
        if (actionInstruction.getParameters().containsKey("containerId")) {
            containerId = (String) actionInstruction.getParameters().get("containerId");
        } else if (actionInstruction.getParameters().containsKey("containerName")) {
            String containerName = (String) actionInstruction.getParameters().get("containerName");
            containerId = dockerService.getContainerId(containerName);
        } else {
            throw new ActionValidationException("Missing containerId or containerName!", actionInstruction.getActionName());
        }
        DockerContainer container = dockerService.getContainerInfo(containerId);
        
        return new ActionResponse(actionInstruction.getActionName(), "SUCCESS!").withBody(container);
    }
    
}
