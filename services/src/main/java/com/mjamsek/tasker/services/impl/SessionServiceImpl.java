package com.mjamsek.tasker.services.impl;

import com.mjamsek.tasker.config.ApplicationConfig;
import com.mjamsek.tasker.entities.exceptions.BadLoginException;
import com.mjamsek.tasker.entities.exceptions.TaskerException;
import com.mjamsek.tasker.entities.persistence.auth.LoginSession;
import com.mjamsek.tasker.services.SessionService;
import com.mjamsek.tasker.utils.RandomStringGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@ApplicationScoped
public class SessionServiceImpl implements SessionService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private ApplicationConfig applicationConfig;
    
    @Override
    public void deleteAllOtherSessions(String ip) {
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery(LoginSession.DELETE_BY_IP);
            query.setParameter("ip", ip);
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
    
    @Override
    public LoginSession startSession(String ip, long userId) {
        try {
            deleteAllOtherSessions(ip);
            String sessionId = generateSessionId();
            Date expirationDate = generateExpirationDate();
        
            LoginSession session = new LoginSession();
            session.setIp(ip);
            session.setUserId(userId);
            session.setSessionId(sessionId);
            session.setExpires(expirationDate);
        
            em.getTransaction().begin();
            em.persist(session);
            em.getTransaction().commit();
            return session;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new TaskerException("Error generating new session!");
        }
    }
    
    @Override
    public LoginSession getSessionByIp(String ip) {
        TypedQuery<LoginSession> query = em.createNamedQuery(LoginSession.FIND_BY_IP, LoginSession.class);
        query.setParameter("ip", ip);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    @Override
    public LoginSession getSession(String sessionId) {
        TypedQuery<LoginSession> query = em.createNamedQuery(LoginSession.FIND_BY_SESSION_ID, LoginSession.class);
        query.setParameter("sessionId", sessionId);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    @Override
    public void removeSession(String sessionId) {
        try {
            LoginSession session = this.getSession(sessionId);
            if (session != null) {
                em.getTransaction().begin();
                em.remove(session);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
    
    @Override
    public LoginSession updateSession(String sessionId) {
        try {
            LoginSession session = getSession(sessionId);
            if (session == null) {
                throw new BadLoginException();
            }
            session.setExpires(generateExpirationDate());
            em.getTransaction().begin();
            em.merge(session);
            em.getTransaction().commit();
            return session;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }
    
    private String generateSessionId() {
        return RandomStringGenerator.generate(20);
    }
    
    private Date generateExpirationDate() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND, applicationConfig.getSessionDuration());
        return calendar.getTime();
    }
}
