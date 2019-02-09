package com.mjamsek.tasker.plugins.docker.actions;

import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.action.ActionValidationException;
import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;
import com.mjamsek.tasker.plugins.docker.services.DockerService;

public class StartContainerAction implements Action {
    
    @Override
    public ActionResponse executeAction(ActionInstruction actionInstruction) {
        DockerService dockerService = DockerService.newInstance(actionInstruction);
        if (actionInstruction.getParameters().containsKey("containerId")) {
            String containerId = (String) actionInstruction.getParameters().get("containerId");
            dockerService.startContainer(containerId);
            return new ActionResponse(actionInstruction.getActionName(), "SUCCESS!");
        } else {
            throw new ActionValidationException("Missing containerId!", actionInstruction.getActionName());
        }
    }
    
}
