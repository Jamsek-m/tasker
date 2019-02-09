package com.mjamsek.tasker.lib.models;

import java.util.HashMap;

public class ActionInstruction {
    
    private String actionName;
    
    private HashMap<String, Object> parameters;
    
    public String getActionName() {
        return actionName;
    }
    
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    
    public HashMap<String, Object> getParameters() {
        return parameters;
    }
    
    public void setParameters(HashMap<String, Object> parameters) {
        this.parameters = parameters;
    }
}
