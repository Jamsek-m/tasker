package com.mjamsek.tasker.entities.persistence.service;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "client_apps")
@DiscriminatorValue("CLIENT_APP")
public class ClientAppServiceEntity extends ServiceEntity {
    
    @Column(name = "application_url")
    private String applicationUrl;
    
    public String getApplicationUrl() {
        return applicationUrl;
    }
    
    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }
}
