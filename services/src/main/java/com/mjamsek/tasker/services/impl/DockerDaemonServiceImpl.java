package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.entities.persistence.service.DockerDaemon;
import com.mjamsek.tasker.services.DockerDaemonService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class DockerDaemonServiceImpl implements DockerDaemonService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
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
    
    @Override
    public DockerDaemon saveDaemon(DockerDaemon daemon) {
        DockerDaemon newDaemon = new DockerDaemon();
        newDaemon.setName(daemon.getName());
        newDaemon.setUrl(daemon.getUrl());
        try {
            em.getTransaction().begin();
            em.persist(newDaemon);
            em.getTransaction().commit();
            return newDaemon;
        } catch (Exception e) {
            em.getTransaction().rollback();
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
            return existingDaemon;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }
    
    @Override
    public void deleteDaemon(long daemonId) {
        DockerDaemon existingDaemon = getDaemon(daemonId);
        if (existingDaemon == null) {
            throw new EntityNotFoundException("Daemon with given id not found!");
        }
        em.remove(existingDaemon);
    }
}
