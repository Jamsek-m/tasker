package com.mjamsek.tasker.plugins.docker.models.recreate;

public class RecreatedContainerData {
    
    private String id;
    
    private String[] warnings;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String[] getWarnings() {
        return warnings;
    }
    
    public void setWarnings(String[] warnings) {
        this.warnings = warnings;
    }
}
