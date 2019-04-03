package com.mjamsek.tasker.entities.persistence.service;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "service_healthchecks")
public class ServiceHealthCheck {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "health_url")
    private String healthUrl;
    
    @OneToMany
    @JoinColumn(name = "healthcheck_id")
    private Set<ServiceHealthCheckFix> fixes;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getHealthUrl() {
        return healthUrl;
    }
    
    public void setHealthUrl(String healthUrl) {
        this.healthUrl = healthUrl;
    }
    
    public Set<ServiceHealthCheckFix> getFixes() {
        return fixes;
    }
    
    public void setFixes(Set<ServiceHealthCheckFix> fixes) {
        this.fixes = fixes;
    }
}
