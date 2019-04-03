package com.mjamsek.tasker.entities.persistence.service;

import javax.persistence.*;

@Entity
@Table(name = "services")
public class Service {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name", unique = true, updatable = false, nullable = false)
    private String name;
    
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "service_url")
    private ServiceUrl serviceUrl;
    
    @ManyToOne
    @JoinColumn(name = "health_check")
    private ServiceHealthCheck healthCheck;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ServiceUrl getServiceUrl() {
        return serviceUrl;
    }
    
    public void setServiceUrl(ServiceUrl serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
    
    public ServiceHealthCheck getHealthCheck() {
        return healthCheck;
    }
    
    public void setHealthCheck(ServiceHealthCheck healthCheck) {
        this.healthCheck = healthCheck;
    }
}
