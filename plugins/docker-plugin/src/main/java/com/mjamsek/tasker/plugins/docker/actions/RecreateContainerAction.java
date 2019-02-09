package com.mjamsek.tasker.plugins.docker.actions;

import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.action.ActionExecutionException;
import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;
import com.mjamsek.tasker.plugins.docker.models.recreate.RecreatedContainerData;
import com.mjamsek.tasker.plugins.docker.services.DockerService;
import com.mjamsek.tasker.plugins.docker.utils.CreateContainerMapper;

public class RecreateContainerAction implements Action {
    
    @Override
    public ActionResponse executeAction(ActionInstruction actionInstruction) throws ActionExecutionException {
        DockerService dockerService = DockerService.newInstance(actionInstruction);
        if (actionInstruction.getParameters().containsKey("containerName")) {
            String containerName = (String) actionInstruction.getParameters().get("containerName");
            RecreatedContainerData recreatedContainer = CreateContainerMapper.fromCreatedContainerData(
                dockerService.recreateContainer(containerName)
            );
            return new ActionResponse(actionInstruction.getActionName(), "SUCCESS!").withBody(recreatedContainer);
        } else {
            throw new ActionExecutionException("Missing containerName!", actionInstruction.getActionName());
        }
    }
    
}
