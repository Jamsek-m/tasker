package com.mjamsek.tasker.services.impl;

import com.mjamsek.tasker.entities.dto.User;
import com.mjamsek.tasker.entities.exceptions.AdminException;
import com.mjamsek.tasker.entities.exceptions.BadLoginException;
import com.mjamsek.tasker.entities.persistence.LoginSession;
import com.mjamsek.tasker.entities.persistence.UserEntity;
import com.mjamsek.tasker.mappers.UserMapper;
import com.mjamsek.tasker.services.AuthService;
import com.mjamsek.tasker.services.UserService;
import com.mjamsek.tasker.utils.RandomStringGenerator;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@RequestScoped
public class AuthServiceImpl implements AuthService {
    
    @Inject
    private HttpServletRequest request;
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private UserService userService;
    
    private final String COOKIE_NAME = "tasker";
    private final int COOKIE_EXPIRATION_SECONDS = 3600;
    
    @Override
    public void loginUser(User dto, HttpServletResponse response) {
        UserEntity user = this.userService.validateLogin(dto);
        LoginSession session = startSession(dto);
        Cookie cookie = new Cookie(COOKIE_NAME, session.getSessionId());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_EXPIRATION_SECONDS);
        cookie.setPath("/");
    
        //TODO: in production pass credentials only by https
        /*String env = ConfigurationUtil.getInstance().get("kumuluzee.env.name").orElse("dev");
        if (env.equals("prod")) {
            cookie.setSecure(true);
        }*/
    
        response.addCookie(cookie);
    }
    
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
    public LoginSession startSession(User user) {
        try {
            String ip = request.getRemoteAddr();
            deleteAllOtherSessions(ip);
            String sessionId = generateSessionId();
            Date expirationDate = generateExpirationDate();
        
            LoginSession session = new LoginSession();
            session.setIp(ip);
            session.setUserId(user.getId());
            session.setSessionId(sessionId);
            session.setExpires(expirationDate);
        
            em.getTransaction().begin();
            em.persist(session);
            em.getTransaction().commit();
            return session;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new AdminException("Error generating new session!");
        }
    }
    
    @Override
    public LoginSession getSession(String ip) {
        TypedQuery<LoginSession> query = em.createNamedQuery(LoginSession.FIND_BY_IP, LoginSession.class);
        query.setParameter("ip", ip);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    @Override
    public void removeLoginSession(String ip) {
        try {
            LoginSession session = this.getSession(ip);
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
    public void logoutUser(HttpServletResponse response) {
        removeLoginSession(request.getRemoteAddr());
        Cookie cookie = new Cookie(COOKIE_NAME, "LOGOUT");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
    
    @Override
    public void refreshLogin() {
        try {
            String ip = request.getRemoteAddr();
            LoginSession session = getSession(ip);
            if (session == null) {
                throw new BadLoginException();
            }
            session.setExpires(generateExpirationDate());
            em.getTransaction().begin();
            em.merge(session);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
    
    @Override
    public void validateAuthorization(boolean refreshToken) {
        String ip = request.getRemoteAddr();
    
        Cookie cookie = getAuthorizationCookie();
        if (cookie == null) {
            throw new BadLoginException();
        }
    
    
        LoginSession session = getSession(ip);
        if (session == null) {
            throw new BadLoginException();
        }
    
        String sessionId = cookie.getValue();
    
        if (!sessionId.equals(session.getSessionId())) {
            throw new BadLoginException();
        }
    
        if (refreshToken) {
            refreshLogin();
        }
    }
    
    @Override
    public User getCurrentUser() {
        String ip = request.getRemoteAddr();
        LoginSession session = getSession(ip);
        if (session == null) {
            return null;
        }
        return UserMapper.fromEntity(userService.getUserById(session.getUserId()));
    }
    
    private Cookie getAuthorizationCookie() {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(COOKIE_NAME)) {
                return cookie;
            }
        }
        return null;
    }
    
    private String generateSessionId() {
        return RandomStringGenerator.generate(20);
    }
    
    private Date generateExpirationDate() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }
}
