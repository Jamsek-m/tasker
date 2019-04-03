package com.mjamsek.tasker.entities.persistence.service;

import javax.persistence.*;

@Entity
@Table(name = "service_healthcheck_fixes")
public class ServiceHealthCheckFix {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "health_check_name")
    private String healthCheckName;
    
    private String fixUrl;
    
    @Enumerated(EnumType.STRING)
    private HealthFixMethod method;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getHealthCheckName() {
        return healthCheckName;
    }
    
    public void setHealthCheckName(String healthCheckName) {
        this.healthCheckName = healthCheckName;
    }
    
    public String getFixUrl() {
        return fixUrl;
    }
    
    public void setFixUrl(String fixUrl) {
        this.fixUrl = fixUrl;
    }
    
    public HealthFixMethod getMethod() {
        return method;
    }
    
    public void setMethod(HealthFixMethod method) {
        this.method = method;
    }
}
