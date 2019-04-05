package com.mjamsek.tasker.services;

import com.mjamsek.tasker.entities.persistence.admin.ConfigEntry;

import java.util.List;

public interface ConfigService {
    
    ConfigEntry getConfig(String key);
    
    List<ConfigEntry> getConfiguration();
    
    void updateConfiguration(ConfigEntry configEntry);
    
    void addConfiguration(ConfigEntry configEntry);
    
}
