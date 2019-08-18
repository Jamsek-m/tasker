package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.exceptions.ConflictException;
import com.mjamsek.tasker.entities.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.entities.persistence.service.DockerEndpointEntity;
import com.mjamsek.tasker.lib.v1.DockerEndpoint;
import com.mjamsek.tasker.lib.v1.enums.LogSeverity;
import com.mjamsek.tasker.lib.v1.exceptions.ValidationException;
import com.mjamsek.tasker.mappers.DockerEndpointMapper;
import com.mjamsek.tasker.services.DockerEndpointService;
import com.mjamsek.tasker.services.LogService;
import com.mjamsek.tasker.services.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ApplicationScoped
public class DockerEndpointServiceImpl implements DockerEndpointService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private LogService logService;
    
    @Inject
    private Validator validator;
    
    @Override
    public List<DockerEndpoint> getEndpoints(QueryParameters queryParameters) {
        return JPAUtils
            .queryEntities(em, DockerEndpointEntity.class, queryParameters)
            .stream()
            .map(DockerEndpointMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public long getEndpointsCount(QueryParameters queryParameters) {
        return JPAUtils.queryEntitiesCount(em, DockerEndpointEntity.class, queryParameters);
    }
    
    @Override
    public DockerEndpointEntity getDockerEndpoint(String endpointId) {
        return em.find(DockerEndpointEntity.class, endpointId);
    }
    
    @Override
    public DockerEndpointEntity getDockerEndpointByName(String endpointName) {
        TypedQuery<DockerEndpointEntity> query = em.createNamedQuery(DockerEndpointEntity.FIND_BY_NAME, DockerEndpointEntity.class);
        query.setParameter("name", endpointName);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    @Override
    public DockerEndpoint saveEndpoint(DockerEndpoint endpoint) {
        this.validateEndpoint(endpoint, true);
        
        DockerEndpointEntity entity = new DockerEndpointEntity();
        entity.setName(endpoint.getName());
        entity.setUrl(endpoint.getUrl());
        
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Docker endpoint '" +
                entity.getName() + "' with id '" + entity.getId() + "' was created.");
            return DockerEndpointMapper.fromEntity(entity);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logService.log(LogSeverity.ERROR, "Error saving daemon '" + entity.getName() + "'!");
            // TODO: throw err
            return null;
        }
    }
    
    @Override
    public DockerEndpoint updateEndpoint(DockerEndpoint endpoint, String endpointId) {
        
        DockerEndpointEntity existingEndpoint = getDockerEndpoint(endpointId);
        if (existingEndpoint == null) {
            throw new EntityNotFoundException("Docker endpoint with given id not found!");
        }
        
        this.validateEndpoint(endpoint, false);
        
        existingEndpoint.setUrl(endpoint.getUrl());
        
        try {
            em.getTransaction().begin();
            em.merge(existingEndpoint);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Daemon '" + existingEndpoint.getName() + "' was updated.");
            return DockerEndpointMapper.fromEntity(existingEndpoint);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logService.log(LogSeverity.ERROR, "Error updating daemon '" + existingEndpoint.getName() + "'!");
            return null;
        }
    }
    
    @Override
    public void deleteEndpoint(String endpointId) {
        DockerEndpointEntity endpoint = this.getDockerEndpoint(endpointId);
        if (endpoint == null) {
            throw new EntityNotFoundException("Daemon with given id not found!");
        }
        try {
            em.getTransaction().begin();
            em.remove(endpoint);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Daemon '" + endpoint.getName() + "' was deleted.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            logService.log(LogSeverity.ERROR, "Error deleting daemon '" + endpoint.getName() + "'!");
        }
    }
    
    private void validateEndpoint(DockerEndpoint endpoint, boolean validateNew) throws ConflictException, ValidationException {
        
        validator.assertNotBlank(endpoint.getName(), "name");
        validator.assertNotBlank(endpoint.getUrl(), "url");
        
        if (validateNew) {
            DockerEndpointEntity existing = getDockerEndpointByName(endpoint.getName());
            if (existing != null) {
                throw new ConflictException("Docker endpoint name is already in use!");
            }
        }
        
        endpoint.setName(endpoint.getName().trim());
        
        Pattern pattern = Pattern.compile("[^a-z0-9_]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(endpoint.getName());
        if (matcher.find()) {
            throw new ValidationException("validation.error.property.name.invalid")
                .isValidationError()
                .withField("name")
                .withDescription("Docker endpoint name can contain only alpha-numeric characters and underscore!");
        }
    }
}
