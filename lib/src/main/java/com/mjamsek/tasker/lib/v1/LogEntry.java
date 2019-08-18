package com.mjamsek.tasker.lib.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mjamsek.tasker.lib.v1.enums.LogSeverity;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogEntry {
    
    private String id;
    
    private String message;
    
    private Date timestamp;
    
    private LogSeverity severity;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public LogSeverity getSeverity() {
        return severity;
    }
    
    public void setSeverity(LogSeverity severity) {
        this.severity = severity;
    }
}
