package com.mjamsek.tasker.plugins.docker.models.config;

public class ConfigEntry {
    
    private long id;
    
    private String key;
    
    private String value;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
