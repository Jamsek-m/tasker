package com.mjamsek.tasker.lib.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiService extends Service {
    
    private String baseUrl;
    
    private String healthcheckUrl;
    
    private Integer majorVersion;
    
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
}
