package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.exceptions.ConflictException;
import com.mjamsek.tasker.entities.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.entities.exceptions.ValidationException;
import com.mjamsek.tasker.entities.persistence.admin.LogSeverity;
import com.mjamsek.tasker.entities.persistence.service.DockerDaemon;
import com.mjamsek.tasker.services.DockerDaemonService;
import com.mjamsek.tasker.services.LogService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class DockerDaemonServiceImpl implements DockerDaemonService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private LogService logService;
    
    @Override
    public List<DockerDaemon> getDaemons(QueryParameters queryParameters) {
        return JPAUtils.queryEntities(em, DockerDaemon.class, queryParameters);
    }
    
    @Override
    public long getDaemonsCount(QueryParameters queryParameters) {
        return JPAUtils.queryEntitiesCount(em, DockerDaemon.class, queryParameters);
    }
    
    @Override
    public DockerDaemon getDaemon(long daemonId) {
        return em.find(DockerDaemon.class, daemonId);
    }
    
    @Override
    public DockerDaemon getDaemon(String deamonName) {
        TypedQuery<DockerDaemon> query = em.createNamedQuery(DockerDaemon.FIND_BY_NAME, DockerDaemon.class);
        query.setParameter("name", deamonName);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    private void validateDaemon(DockerDaemon daemon) {
        if (daemon.getName().isEmpty()) {
            throw new ValidationException("Daemon name is empty!");
        }
        if (daemon.getUrl().isEmpty()) {
            throw new ValidationException("Daemon url is empty!");
        }
        
        DockerDaemon existing = getDaemon(daemon.getName());
        if (existing != null) {
            throw new ConflictException("Daemon name is already in use!");
        }
        
        daemon.setName(daemon.getName().trim());
    
        Pattern pattern = Pattern.compile("[^a-z0-9_]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(daemon.getName());
        if (matcher.find()) {
            throw new ValidationException("Daemon name can contain only alpha-numeric characters!");
        }
    }
    
    @Override
    public DockerDaemon saveDaemon(DockerDaemon daemon) {
        this.validateDaemon(daemon);
        
        DockerDaemon newDaemon = new DockerDaemon();
        newDaemon.setName(daemon.getName());
        newDaemon.setUrl(daemon.getUrl());
        try {
            em.getTransaction().begin();
            em.persist(newDaemon);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Daemon '" + newDaemon.getName() + "' was created.");
            return newDaemon;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logService.log(LogSeverity.ERROR, "Error saving daemon '" + newDaemon.getName() + "'!");
            return null;
        }
    }
    
    @Override
    public DockerDaemon updateDaemon(DockerDaemon daemon, long daemonId) {
        DockerDaemon existingDaemon = getDaemon(daemonId);
        if (existingDaemon == null) {
            throw new EntityNotFoundException("Daemon with given id not found!");
        }
        existingDaemon.setUrl(daemon.getUrl());
        try {
            em.getTransaction().begin();
            em.merge(existingDaemon);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Daemon '" + existingDaemon.getName() + "' was updated.");
            return existingDaemon;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logService.log(LogSeverity.ERROR, "Error updating daemon '" + existingDaemon.getName() + "'!");
            return null;
        }
    }
    
    @Override
    public void deleteDaemon(long daemonId) {
        DockerDaemon existingDaemon = getDaemon(daemonId);
        if (existingDaemon == null) {
            throw new EntityNotFoundException("Daemon with given id not found!");
        }
        try {
            em.getTransaction().begin();
            em.remove(existingDaemon);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Daemon '" + existingDaemon.getName() + "' was deleted.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            logService.log(LogSeverity.ERROR, "Error deleting daemon '" + existingDaemon.getName() + "'!");
        }
    }
}
