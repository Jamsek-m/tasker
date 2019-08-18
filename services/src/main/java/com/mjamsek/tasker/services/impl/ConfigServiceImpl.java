package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import com.mjamsek.tasker.entities.exceptions.ConflictException;
import com.mjamsek.tasker.entities.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.entities.persistence.admin.ConfigEntryEntity;
import com.mjamsek.tasker.lib.v1.ConfigEntry;
import com.mjamsek.tasker.lib.v1.enums.LogSeverity;
import com.mjamsek.tasker.mappers.ConfigEntryMapper;
import com.mjamsek.tasker.services.ConfigService;
import com.mjamsek.tasker.services.LogService;
import com.mjamsek.tasker.services.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ConfigServiceImpl implements ConfigService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private LogService logService;
    
    @Inject
    private Validator validator;
    
    @Override
    public ConfigEntry getConfig(String key) {
        TypedQuery<ConfigEntryEntity> query = em.createNamedQuery(ConfigEntryEntity.FIND_BY_KEY, ConfigEntryEntity.class);
        query.setParameter("key", key);
        try {
            ConfigEntryEntity entity = query.getSingleResult();
            return ConfigEntryMapper.fromEntity(entity);
        } catch (NoResultException exc) {
            logService.log(LogSeverity.ERROR, "Configuration with key '" + key + "' does not exists.");
            return null;
        }
    }
    
    @Override
    public List<ConfigEntry> getConfiguration(QueryParameters queryParameters) {
        return JPAUtils
            .queryEntities(em, ConfigEntryEntity.class, queryParameters)
            .stream()
            .map(ConfigEntryMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public long getConfigurationCount(QueryParameters queryParameters) {
        return JPAUtils.queryEntitiesCount(em, ConfigEntryEntity.class, queryParameters);
    }
    
    @Override
    public void updateConfiguration(ConfigEntry configEntry, String entryId) {
    
        ConfigEntryEntity entity = em.find(ConfigEntryEntity.class, entryId);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        validator.assertNotBlank(configEntry.getValue(), "value");
        entity.setValue(configEntry.getValue());
        
        try {
            em.getTransaction().begin();
            em.merge(entity);
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
        
        validator.assertNotBlank(configEntry.getKey(), "key");
        validator.assertNotBlank(configEntry.getValue(), "value");
        
        ConfigEntry existing = this.getConfig(configEntry.getKey());
        if (existing != null) {
            throw new ConflictException("Configuration with given key already exists!");
        }
        
        ConfigEntryEntity entity = ConfigEntryMapper.toEntity(configEntry);
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Configuration '" + configEntry.getKey() + "' was created.");
        } catch (Exception exc) {
            exc.printStackTrace();
            logService.log(LogSeverity.ERROR, "Error adding new configuration");
            em.getTransaction().rollback();
        }
    }
    
    @Override
    public void deleteConfiguration(String configId) {
        ConfigEntryEntity configEntry = em.find(ConfigEntryEntity.class, configId);
        if (configEntry != null) {
            try {
                em.getTransaction().begin();
                em.remove(configEntry);
                em.getTransaction().commit();
            } catch (Exception exc) {
                exc.printStackTrace();
                em.getTransaction().rollback();
            }
        }
    }
}
