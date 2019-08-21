package com.mjamsek.tasker.entities.persistence;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "domains")
public class DomainEntity {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Column
    private String domain;
    
    @Column(name = "ssl_enabled")
    private Boolean sslEnabled;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getDomain() {
        return domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    public Boolean getSslEnabled() {
        return sslEnabled;
    }
    
    public void setSslEnabled(Boolean hasCertificate) {
        this.sslEnabled = hasCertificate;
    }
}
