package com.mjamsek.tasker.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.entities.persistence.admin.LogEntry;
import com.mjamsek.tasker.entities.persistence.admin.LogSeverity;

import java.util.List;

public interface LogService {
    
    List<LogEntry> getLogs(QueryParameters query);
    
    long getLogsCount(QueryParameters queryParameters);
    
    void log(LogSeverity severity, String message);
}
