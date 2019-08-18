package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.persistence.admin.LogEntryEntity;
import com.mjamsek.tasker.lib.v1.LogEntry;
import com.mjamsek.tasker.lib.v1.enums.LogSeverity;
import com.mjamsek.tasker.mappers.LogEntryMapper;
import com.mjamsek.tasker.services.LogService;


import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LogServiceImpl implements LogService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Override
    public List<LogEntry> getLogs(QueryParameters query) {
        return JPAUtils
            .queryEntities(em, LogEntryEntity.class, query)
            .stream()
            .map(LogEntryMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public long getLogsCount(QueryParameters queryParameters) {
        return JPAUtils.queryEntitiesCount(em, LogEntryEntity.class, queryParameters);
    }
    
    @Override
    public void log(LogSeverity severity, String message) {
        try {
            LogEntryEntity entity = new LogEntryEntity();
            entity.setTimestamp(new Date());
            entity.setMessage(message);
            entity.setSeverity(severity);
        
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
}
