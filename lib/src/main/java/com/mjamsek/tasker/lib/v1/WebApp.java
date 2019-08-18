package com.mjamsek.tasker.lib.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebApp extends Service {
    
    private String baseUrl;
    
    private String healthcheckUrl;
    
    private Integer majorVersion;
    
    private String applicationUrl;
    
    public String getBaseUrl() {
        return baseUrl;
    }
    
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public String getHealthcheckUrl() {
        return healthcheckUrl;
    }
    
    public void setHealthcheckUrl(String healthcheckUrl) {
        this.healthcheckUrl = healthcheckUrl;
    }
    
    public Integer getMajorVersion() {
        return majorVersion;
    }
    
    public void setMajorVersion(Integer majorVersion) {
        this.majorVersion = majorVersion;
    }
    
    public String getApplicationUrl() {
        return applicationUrl;
    }
    
    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }
}
