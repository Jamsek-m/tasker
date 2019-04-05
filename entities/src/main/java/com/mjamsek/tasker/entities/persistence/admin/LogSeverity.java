package com.mjamsek.tasker.entities.persistence.admin;

public enum LogSeverity {

    INFO("INFO"),
    DEBUG("DEBUG"),
    WARNING("WARNING"),
    ERROR("ERROR");
    
    private final String severity;
    
    private LogSeverity(String severity) {
        this.severity = severity;
    }
    
    public String getSeverity() {
        return this.severity;
    }
}
