package com.mjamsek.tasker.entities.persistence.service;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "api_services")
@DiscriminatorValue("API_SERVICE")
public class ApiServiceEntity extends ServiceEntity {

    @Column(name = "base_url")
    private String baseUrl;
    
    @Column(name = "healthcheck_url")
    private String healthcheckUrl;
    
    @Column(name = "major_version")
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
