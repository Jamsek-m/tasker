package com.mjamsek.tasker.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.lib.v1.ConfigEntry;

import java.util.List;

public interface ConfigService {
    
    ConfigEntry getConfig(String key);
    
    List<ConfigEntry> getConfiguration(QueryParameters queryParameters);
    
    long getConfigurationCount(QueryParameters queryParameters);
    
    void updateConfiguration(ConfigEntry configEntry, String entryId);
    
    void addConfiguration(ConfigEntry configEntry);
    
    void deleteConfiguration(String configId);
    
}
