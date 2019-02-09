package com.mjamsek.tasker.admin.entities;

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
