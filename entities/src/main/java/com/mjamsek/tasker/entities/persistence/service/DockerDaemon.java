package com.mjamsek.tasker.entities.persistence.service;

import javax.persistence.*;

@Entity
@Table(name = "docker_daemons", indexes = @Index(name = "NAME_UNIQ_INDEX", columnList = "name", unique = true))
@NamedQueries({
    @NamedQuery(name = DockerDaemon.FIND_BY_NAME, query = "SELECT d FROM DockerDaemon d WHERE d.name = :name")
})
public class DockerDaemon {
    
    public static final String FIND_BY_NAME = "DockerDaemon.findByName";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(unique = true, updatable = false, nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String url;
    
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
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}
