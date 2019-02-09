package com.mjamsek.tasker.admin.services;

import com.mjamsek.tasker.admin.entities.ConfigEntry;
import com.mjamsek.tasker.admin.entities.LogSeverity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

@ApplicationScoped
public class ConfigService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private LogService logService;
    
    public ConfigEntry getConfig(String key) {
        TypedQuery<ConfigEntry> query = em.createNamedQuery("Config.find", ConfigEntry.class);
        query.setParameter("key", key);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            logService.log(LogSeverity.ERROR, "Configuration with key '" + key + "' does not exists.");
            return null;
        }
    }
    
    public List<ConfigEntry> getConfiguration() {
        Query query = em.createNamedQuery("Config.findAll");
        return query.getResultList();
    }
    
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
