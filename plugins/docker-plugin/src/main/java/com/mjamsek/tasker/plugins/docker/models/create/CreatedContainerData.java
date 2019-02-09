package com.mjamsek.tasker.plugins.docker.models.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatedContainerData {
    
    @JsonProperty("Id")
    private String Id;
    
    @JsonProperty("Warnings")
    private String[] Warnings;
    
    public String getId() {
        return Id;
    }
    
    public void setId(String id) {
        Id = id;
    }
    
    public String[] getWarnings() {
        return Warnings;
    }
    
    public void setWarnings(String[] warnings) {
        Warnings = warnings;
    }
}
