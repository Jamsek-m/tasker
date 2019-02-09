package com.mjamsek.tasker.admin.services;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.mjamsek.tasker.admin.entities.LoginSession;
import com.mjamsek.tasker.admin.entities.User;
import com.mjamsek.tasker.admin.entities.dto.UserDTO;
import com.mjamsek.tasker.admin.entities.exceptions.AdminException;
import com.mjamsek.tasker.admin.entities.exceptions.BadLoginException;
import com.mjamsek.tasker.admin.utils.RandomStringGenerator;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@RequestScoped
public class AuthService {
    
    @Inject
    private HttpServletRequest request;
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private UserService userService;
    
    private final String COOKIE_NAME = "tasker";
    private final int COOKIE_EXPIRATION_SECONDS = 3600;
    
    public HttpServletResponse loginUser(UserDTO dto, HttpServletResponse response) {
        User user = this.userService.validateLogin(dto);
        LoginSession session = startLoginSession(user);
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
        return response;
    }
    
    public void deleteAllOtherSessions(String ip) {
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("LoginSession.deleteByIp");
            query.setParameter("ip", ip);
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
    
    public LoginSession startLoginSession(User user) {
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
    
    public LoginSession getLoginSession(String ip) {
        TypedQuery<LoginSession> query = em.createNamedQuery("LoginSession.findByIp", LoginSession.class);
        query.setParameter("ip", ip);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    public void removeLoginSession(String ip) {
        try {
            LoginSession session = this.getLoginSession(ip);
            if (session != null) {
                em.getTransaction().begin();
                em.remove(session);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
    
    public HttpServletResponse logoutUser(HttpServletResponse response) {
        removeLoginSession(request.getRemoteAddr());
        Cookie cookie = new Cookie(COOKIE_NAME, "LOGOUT");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return response;
    }
    
    public void refreshLogin() {
        try {
            String ip = request.getRemoteAddr();
            LoginSession session = getLoginSession(ip);
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
    
    public void validateAuthorization(boolean refreshToken) {
        String ip = request.getRemoteAddr();
        if (ip.equals("127.0.0.1") || ip.equals("localhost")) {
            return;
        }
        
        Cookie cookie = getAuthorizationCookie();
        if (cookie == null) {
            throw new BadLoginException();
        }
        
        
        LoginSession session = getLoginSession(ip);
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
    
    /**
     * Retrieves current user
     * @return current user if logged in, null otherwise
     */
    public User getCurrentUser() {
        String ip = request.getRemoteAddr();
        LoginSession session = getLoginSession(ip);
        if (session == null) {
            return null;
        }
        return userService.getUserById(session.getUserId());
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
