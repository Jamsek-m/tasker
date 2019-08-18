package com.mjamsek.tasker.lib.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mjamsek.tasker.lib.v1.enums.ServiceType;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type", visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ApiService.class, name = "API_SERVICE"),
    @JsonSubTypes.Type(value = WebApp.class, name = "WEB_APP"),
    @JsonSubTypes.Type(value = ClientApp.class, name = "CLIENT_APP")
})
public class Service {
    
    protected String id;
    
    protected String name;
    
    protected String description;
    
    protected String version;
    
    protected ServiceType type;
    
    protected Boolean active;
    
    protected ServiceDeployment deployment;
    
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
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public ServiceDeployment getDeployment() {
        return deployment;
    }
    
    public void setDeployment(ServiceDeployment deployment) {
        this.deployment = deployment;
    }
    
    public ServiceType getType() {
        return type;
    }
    
    public void setType(ServiceType type) {
        this.type = type;
    }
}
