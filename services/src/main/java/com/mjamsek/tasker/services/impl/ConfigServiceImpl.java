package com.mjamsek.tasker.services.impl;

import com.mjamsek.tasker.entities.persistence.admin.ConfigEntry;
import com.mjamsek.tasker.entities.persistence.admin.LogSeverity;
import com.mjamsek.tasker.services.ConfigService;
import com.mjamsek.tasker.services.LogService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class ConfigServiceImpl implements ConfigService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private LogService logService;
    
    @Override
    public ConfigEntry getConfig(String key) {
        TypedQuery<ConfigEntry> query = em.createNamedQuery(ConfigEntry.FIND_BY_KEY, ConfigEntry.class);
        query.setParameter("key", key);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            logService.log(LogSeverity.ERROR, "Configuration with key '" + key + "' does not exists.");
            return null;
        }
    }
    
    @Override
    public List<ConfigEntry> getConfiguration() {
        TypedQuery<ConfigEntry> query = em.createNamedQuery(ConfigEntry.FIND_ALL, ConfigEntry.class);
        return query.getResultList();
    }
    
    @Override
    public void updateConfiguration(ConfigEntry configEntry) {
        try {
            em.getTransaction().begin();
            em.merge(configEntry);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Configuration '" + configEntry.getKey() + "' was updated.");
        } catch (Exception exc) {
            exc.printStackTrace();
            logService.log(LogSeverity.ERROR, "Error updating configuration");
            em.getTransaction().rollback();
        }
    }
    
    @Override
    public void addConfiguration(ConfigEntry configEntry) {
        try {
            em.getTransaction().begin();
            em.persist(configEntry);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Configuration '" + configEntry.getKey() + "' was created.");
        } catch (Exception exc) {
            exc.printStackTrace();
            logService.log(LogSeverity.ERROR, "Error adding new configuration");
            em.getTransaction().rollback();
        }
    }
}
