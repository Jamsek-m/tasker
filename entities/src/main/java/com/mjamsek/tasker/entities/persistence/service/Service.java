package com.mjamsek.tasker.entities.persistence.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "services", indexes = @Index(name = "NAME_UNIQ_INDEX", columnList = "name", unique = true))
@NamedQueries({
    @NamedQuery(name = Service.FIND_BY_NAME, query = "SELECT s FROM Service s WHERE s.name = :name")
})
public class Service {
    
    public static final String FIND_BY_NAME = "Service.findByName";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name", unique = true, updatable = false, nullable = false)
    private String name;
    
    private String description;
    
    @JsonIgnore
    private String token;
    
    @JsonIgnore
    private Boolean active;
    
    @ManyToOne
    @JoinColumn(name = "deployment_id")
    private ServiceDeployment deployment;
    
    @ManyToOne
    @JoinColumn(name = "url_id")
    private ServiceUrl serviceUrl;
    
    @ManyToOne
    @JoinColumn(name = "health_check_id")
    private ServiceHealthCheck healthCheck;
    
    public long getId() {
        return id;
    }
    
    public Boolean isActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
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
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
}
