package com.mjamsek.tasker.entities.dto;

public class ServiceToken {
    
    private String token;
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public ServiceToken() {
    }
    
    public ServiceToken(String token) {
        this.token = token;
    }
}
