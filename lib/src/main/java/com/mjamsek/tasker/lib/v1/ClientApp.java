package com.mjamsek.tasker.lib.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientApp extends Service {
    
    private String applicationUrl;
    
    public String getApplicationUrl() {
        return applicationUrl;
    }
    
    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }
}
