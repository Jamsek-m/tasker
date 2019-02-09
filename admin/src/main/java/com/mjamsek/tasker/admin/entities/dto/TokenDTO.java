package com.mjamsek.tasker.admin.entities.dto;

import java.util.Date;
import java.util.List;

public class TokenDTO {

    private long id;
    
    private String name;
    
    private Date expired;
    
    private List<String> allowedActions;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
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
    
    public Date getExpired() {
        return expired;
    }
    
    public void setExpired(Date expired) {
        this.expired = expired;
    }
}
