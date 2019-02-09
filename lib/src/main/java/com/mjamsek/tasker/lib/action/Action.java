package com.mjamsek.tasker.lib.action;

import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;

public interface Action {
    
    ActionResponse executeAction(ActionInstruction actionInstruction) throws ActionExecutionException;
}
