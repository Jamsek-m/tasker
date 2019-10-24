package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.persistence.ServerEntity;
import com.mjamsek.tasker.lib.v1.Server;
import com.mjamsek.tasker.lib.v1.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.lib.v1.exceptions.PersistenceException;
import com.mjamsek.tasker.mappers.ServerMapper;
import com.mjamsek.tasker.services.ServerService;
import com.mjamsek.tasker.services.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServerServiceImpl implements ServerService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private Validator validator;
    
    @Override
    public List<Server> getServers(QueryParameters queryParameters) {
        return JPAUtils
            .queryEntities(em, ServerEntity.class, queryParameters)
            .stream()
            .map(ServerMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public long getServersCount(QueryParameters queryParameters) {
        return JPAUtils.queryEntitiesCount(em, ServerEntity.class, queryParameters);
    }
    
    @Override
    public Server getServer(String serverId) throws EntityNotFoundException {
        ServerEntity entity = em.find(ServerEntity.class, serverId);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return ServerMapper.fromEntity(entity);
    }
    
    @Override
    public Server createServer(Server server) throws PersistenceException {
        ServerEntity entity = ServerMapper.toEntity(server);
        validator.assertNotNull(entity.getName(), "name");
        validator.assertNotNull(entity.getIpAddress(), "ipAddress");
        validator.assertRegex(
            entity.getIpAddress(),
            "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
            "ipAddress"
        );
        
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return ServerMapper.fromEntity(entity);
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new PersistenceException();
        }
    }
    
    @Override
    public Server updateServer(Server server, String serverId) throws PersistenceException {
        validator.assertNotNull(server.getName(), "name");
        validator.assertNotNull(server.getIpAddress(), "ipAddress");
        validator.assertRegex(
            server.getIpAddress(),
            "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
            "ipAddress"
        );
        
        ServerEntity entity = em.find(ServerEntity.class, serverId);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        entity.setName(server.getName());
        entity.setIpAddress(server.getIpAddress());
    
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            return ServerMapper.fromEntity(entity);
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new PersistenceException();
        }
    }
    
    @Override
    public void deleteServer(String serverId) throws PersistenceException {
        ServerEntity entity = em.find(ServerEntity.class, serverId);
        if (entity != null) {
            try {
                em.getTransaction().begin();
                em.remove(entity);
                em.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                em.getTransaction().rollback();
                throw new PersistenceException();
            }
        }
    }
}
