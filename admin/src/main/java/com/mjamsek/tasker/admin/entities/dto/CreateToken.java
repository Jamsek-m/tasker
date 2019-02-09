package com.mjamsek.tasker.admin.entities.dto;

import java.util.List;

public class CreateToken {
    
    private String name;
    
    private List<String> allowedActions;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<String> getAllowedActions() {
        return allowedActions;
    }
    
    public void setAllowedActions(List<String> allowedActions) {
        this.allowedActions = allowedActions;
    }
}
