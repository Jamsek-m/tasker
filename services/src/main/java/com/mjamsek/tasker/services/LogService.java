package com.mjamsek.tasker.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.lib.v1.LogEntry;
import com.mjamsek.tasker.lib.v1.enums.LogSeverity;

import java.util.List;

public interface LogService {
    
    List<LogEntry> getLogs(QueryParameters query);
    
    long getLogsCount(QueryParameters queryParameters);
    
    void log(LogSeverity severity, String message);
}
