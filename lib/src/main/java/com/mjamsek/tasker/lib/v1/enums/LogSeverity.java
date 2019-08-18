package com.mjamsek.tasker.lib.v1.enums;

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
