package com.mjamsek.tasker.lib.action;

public class ActionExecutionException extends RuntimeException {
    
    private String actionName;
    
    public ActionExecutionException(String message, String actionName) {
        super(message);
        this.actionName = actionName;
    }
    
    public String getActionName() {
        return actionName;
    }
}
