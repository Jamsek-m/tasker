package com.mjamsek.tasker.entities.persistence;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "logs")
public class LogEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String severity;
    
    @Column
    private String message;
    
    @Column(name = "log_date")
    private Date logDate;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Date getLogDate() {
        return logDate;
    }
    
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }
}
