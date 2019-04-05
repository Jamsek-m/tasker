package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.persistence.admin.LogEntry;
import com.mjamsek.tasker.entities.persistence.admin.LogSeverity;
import com.mjamsek.tasker.services.LogService;


import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class LogServiceImpl implements LogService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Override
    public List<LogEntry> getLogs(QueryParameters query) {
        return JPAUtils.queryEntities(em, LogEntry.class, query);
    }
    
    @Override
    public long getLogsCount(QueryParameters queryParameters) {
        return JPAUtils.queryEntitiesCount(em, LogEntry.class, queryParameters);
    }
    
    @Override
    public void log(LogSeverity severity, String message) {
        try {
            LogEntry entry = new LogEntry();
            entry.setLogDate(new Date());
            entry.setMessage(message);
            entry.setSeverity(severity.getSeverity());
        
            em.getTransaction().begin();
            em.persist(entry);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
}
