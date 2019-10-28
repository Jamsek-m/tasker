package com.mjamsek.tasker.services.impl;

import com.mjamsek.tasker.entities.persistence.service.ServiceTokenEntity;
import com.mjamsek.tasker.lib.v1.common.HttpHeader;
import com.mjamsek.tasker.lib.v1.exceptions.TaskerException;
import com.mjamsek.tasker.lib.v1.exceptions.UnauthorizedException;
import com.mjamsek.tasker.services.ServicesPublicService;
import com.mjamsek.tasker.services.ServicesService;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

@RequestScoped
public class ServicesPublicServiceImpl implements ServicesPublicService {
    
    @Context
    private HttpServletRequest request;
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private ServicesService servicesService;
    
    @Override
    public void startContainer(String serviceId) {
        this.verifyToken(serviceId);
        servicesService.startContainer(serviceId);
    }
    
    @Override
    public void recreateContainer(String serviceId) {
        this.verifyToken(serviceId);
        servicesService.recreateContainer(serviceId);
    }
    
    private void verifyToken(String serviceId) {
        String[] token = getAuthToken().split("\\.");
        
        if (token.length != 2) {
            throw new UnauthorizedException();
        }
        
        TypedQuery<ServiceTokenEntity> query = em.createNamedQuery(ServiceTokenEntity.FIND_BY_TOKEN_ID, ServiceTokenEntity.class);
        query.setParameter("tokenId", token[0]);
        
        try {
            ServiceTokenEntity tokenEntity = query.getSingleResult();
            if (!serviceId.equals(tokenEntity.getService().getId())) {
                throw new UnauthorizedException();
            }
            if (!BCrypt.checkpw(token[1], tokenEntity.getToken())) {
                throw new UnauthorizedException();
            }
        } catch (NonUniqueResultException | NoResultException e) {
            throw new UnauthorizedException();
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new TaskerException(e.getMessage());
        }
    }
    
    private String getAuthToken() {
        String authToken = request.getHeader(HttpHeader.X_TASKER_KEY);
        return authToken == null ? "" : authToken;
    }
}
