package com.mjamsek.tasker.entities.persistence.service;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service_deployments")
public class ServiceDeploymentEntity {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Column(name = "container_id")
    private String containerId;
    
    @Column(name = "container_name")
    private String containerName;
    
    @ManyToOne
    @JoinColumn(name = "docker_endpoint_id")
    private DockerEndpointEntity dockerEndpoint;
    
    @OneToMany(mappedBy = "service")
    private List<ServiceTokenEntity> services;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getContainerId() {
        return containerId;
    }
    
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
    
    public String getContainerName() {
        return containerName;
    }
    
    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }
    
    public DockerEndpointEntity getDockerEndpoint() {
        return dockerEndpoint;
    }
    
    public void setDockerEndpoint(DockerEndpointEntity dockerEndpoint) {
        this.dockerEndpoint = dockerEndpoint;
    }
    
    public List<ServiceTokenEntity> getServices() {
        return services;
    }
    
    public void setServices(List<ServiceTokenEntity> services) {
        this.services = services;
    }
}
