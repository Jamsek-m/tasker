package com.mjamsek.tasker.lib.models;

public class ActionResponse {
    
    private String actionName;
    
    private String status;
    
    private Object actionResponse;
    
    public ActionResponse() {
    
    }
    
    public ActionResponse(String actionName) {
        this.actionName = actionName;
    }
    
    public ActionResponse(String actionName, String status) {
        this.actionName = actionName;
        this.status = status;
    }
    
    public ActionResponse withBody(Object body) {
        this.actionResponse = body;
        return this;
    }
    
    public String getActionName() {
        return actionName;
    }
    
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Object getActionResponse() {
        return actionResponse;
    }
    
    public void setActionResponse(Object actionResponse) {
        this.actionResponse = actionResponse;
    }
}
