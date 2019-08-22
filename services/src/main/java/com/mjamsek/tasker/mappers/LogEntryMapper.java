package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.entities.persistence.admin.LogEntryEntity;
import com.mjamsek.tasker.lib.v1.LogEntry;

public class LogEntryMapper {
    
    public static LogEntryEntity toEntity(LogEntry entry) {
        LogEntryEntity entity = new LogEntryEntity();
        entity.setMessage(entry.getMessage());
        entity.setSeverity(entry.getSeverity());
        entity.setTimestamp(entry.getTimestamp());
        return entity;
    }
    
    public static LogEntry fromEntity(LogEntryEntity entity) {
        LogEntry entry = new LogEntry();
        entry.setId(entity.getId());
        entry.setMessage(entity.getMessage());
        entry.setSeverity(entity.getSeverity());
        entry.setTimestamp(entity.getTimestamp());
        return entry;
    }
}
