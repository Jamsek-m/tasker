package com.mjamsek.tasker.tasks.shell;

import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.annotations.Task;
import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;

@Task(name = "shellCmd", description = "Executes shell command on server.")
public class ShellCmdTask implements Action {
    
    @Override
    public ActionResponse executeAction(ActionInstruction actionInstruction) {
        return new ActionResponse(actionInstruction.getActionName(), "NOT IMPLEMENTED!");
    }
}
