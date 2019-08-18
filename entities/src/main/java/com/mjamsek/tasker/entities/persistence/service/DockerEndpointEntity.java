package com.mjamsek.tasker.entities.persistence.service;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "docker_endpoints", indexes = @Index(name = "NAME_UNIQ_INDEX", columnList = "name", unique = true))
@NamedQueries({
    @NamedQuery(name = DockerEndpointEntity.FIND_BY_NAME, query = "SELECT d FROM DockerEndpointEntity d WHERE d.name = :name")
})
public class DockerEndpointEntity {
    
    public static final String FIND_BY_NAME = "DockerEndpointEntity.findByName";
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Column(unique = true, updatable = false, nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String url;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
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
