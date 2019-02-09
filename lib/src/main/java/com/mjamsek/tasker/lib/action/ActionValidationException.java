package com.mjamsek.tasker.lib.action;

public class ActionValidationException extends RuntimeException {
    
    private String actionName;
    
    public ActionValidationException(String message, String actionName) {
        super(message);
        this.actionName = actionName;
    }
    
    public String getActionName() {
        return actionName;
    }
}
