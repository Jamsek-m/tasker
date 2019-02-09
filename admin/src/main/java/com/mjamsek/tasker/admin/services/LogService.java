package com.mjamsek.tasker.admin.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.admin.entities.LogEntry;
import com.mjamsek.tasker.admin.entities.LogSeverity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class LogService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    public List<LogEntry> getLogs(QueryParameters query) {
        return JPAUtils.queryEntities(em, LogEntry.class, query);
    }
    
    public long getLogsCount(QueryParameters queryParameters) {
        return JPAUtils.queryEntitiesCount(em, LogEntry.class, queryParameters);
    }
    
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
