package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.persistence.DomainEntity;
import com.mjamsek.tasker.lib.v1.Domain;
import com.mjamsek.tasker.lib.v1.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.lib.v1.exceptions.PersistenceException;
import com.mjamsek.tasker.mappers.DomainMapper;
import com.mjamsek.tasker.services.DomainService;
import com.mjamsek.tasker.services.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DomainServiceImpl implements DomainService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private Validator validator;
    
    @Override
    public List<Domain> getDomains(QueryParameters queryParameters) {
        return JPAUtils
            .queryEntities(em, DomainEntity.class, queryParameters)
            .stream()
            .map(DomainMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public long getDomainsCount(QueryParameters queryParameters) {
        return JPAUtils.queryEntitiesCount(em, DomainEntity.class, queryParameters);
    }
    
    @Override
    public Domain getDomain(String domainId) {
        DomainEntity entity = em.find(DomainEntity.class, domainId);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return DomainMapper.fromEntity(entity);
    }
    
    @Override
    public Domain createDomain(Domain domain) {
        DomainEntity entity = DomainMapper.toEntity(domain);
        validator.assertNotBlank(entity.getDomain(), "domain");
        
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return DomainMapper.fromEntity(entity);
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new PersistenceException();
        }
    }
    
    @Override
    public Domain updateDomain(Domain domain, String domainId) {
        validator.assertNotBlank(domain.getDomain(), "domain");
    
        DomainEntity entity = em.find(DomainEntity.class, domainId);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        entity.setDomain(domain.getDomain());
        entity.setSslEnabled(domain.getSslEnabled());
        
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            return DomainMapper.fromEntity(entity);
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new PersistenceException();
        }
    }
    
    @Override
    public void deleteDomain(String domainId) {
        DomainEntity entity = em.find(DomainEntity.class, domainId);
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
