package com.mjamsek.tasker.entities.persistence.service;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "services", indexes = {
    @Index(name = "NAME_UNIQ_INDEX", columnList = "name,version", unique = true)
})
@DiscriminatorColumn(name = "service_type", discriminatorType = DiscriminatorType.STRING, length = 50)
@NamedQueries({
    @NamedQuery(name = ServiceEntity.FIND_BY_NAME, query = "SELECT s FROM ServiceEntity s WHERE s.name = :name"),
    @NamedQuery(name = ServiceEntity.FIND_BY_NAME_AND_VERSION, query = "SELECT s FROM ServiceEntity s WHERE s.name = :name AND s.version = :version")
})
public class ServiceEntity {
    
    public static final String FIND_BY_NAME = "ServiceEntity.findByName";
    public static final String FIND_BY_NAME_AND_VERSION = "ServiceEntity.findByNameAndVersion";
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;
    
    @Column
    protected String name;
    
    @Column
    protected String description;
    
    @Column
    protected String version;
    
    @Column
    protected Boolean active;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "deployment_id")
    protected ServiceDeploymentEntity deployment;
    
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public ServiceDeploymentEntity getDeployment() {
        return deployment;
    }
    
    public void setDeployment(ServiceDeploymentEntity deployment) {
        this.deployment = deployment;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
}
