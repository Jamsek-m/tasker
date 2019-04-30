package com.mjamsek.tasker.entities.persistence.service;

import javax.persistence.*;

@Entity
@Table(name = "service_urls")
public class ServiceUrl {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String url;
    
    private String version;
    
    @Column(name = "url_versioning")
    private boolean urlVersioning;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public boolean isUrlVersioning() {
        return urlVersioning;
    }
    
    public void setUrlVersioning(boolean urlVersioning) {
        this.urlVersioning = urlVersioning;
    }
}
