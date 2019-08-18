package com.mjamsek.tasker.entities.persistence.service;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "web_apps")
@DiscriminatorValue("WEB_APP")
public class WebAppServiceEntity extends ServiceEntity {
    
    @Column(name = "base_url")
    private String baseUrl;
    
    @Column(name = "healthcheck_url")
    private String healthcheckUrl;
    
    @Column(name = "major_version")
    private Integer majorVersion;
    
    @Column(name = "application_url")
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
