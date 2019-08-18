package com.mjamsek.tasker.entities.persistence.admin;

import com.mjamsek.tasker.lib.v1.enums.LogSeverity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "logs")
public class LogEntryEntity {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Column
    @Enumerated(EnumType.STRING)
    private LogSeverity severity;
    
    @Column
    private String message;
    
    @Column()
    private Date timestamp;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public LogSeverity getSeverity() {
        return severity;
    }
    
    public void setSeverity(LogSeverity severity) {
        this.severity = severity;
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
}
