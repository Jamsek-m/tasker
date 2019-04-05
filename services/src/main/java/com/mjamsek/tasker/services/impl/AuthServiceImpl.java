package com.mjamsek.tasker.services.impl;

import com.mjamsek.tasker.auth.SecurityConstants;
import com.mjamsek.tasker.config.ApplicationConfig;
import com.mjamsek.tasker.config.Environments;
import com.mjamsek.tasker.config.ServerConfig;
import com.mjamsek.tasker.entities.dto.User;
import com.mjamsek.tasker.entities.exceptions.BadLoginException;
import com.mjamsek.tasker.entities.persistence.auth.LoginSession;
import com.mjamsek.tasker.entities.persistence.auth.UserEntity;
import com.mjamsek.tasker.mappers.UserMapper;
import com.mjamsek.tasker.services.AuthService;
import com.mjamsek.tasker.services.SessionService;
import com.mjamsek.tasker.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestScoped
public class AuthServiceImpl implements AuthService {
    
    @Inject
    private HttpServletRequest request;
    
    @Inject
    private UserService userService;
    
    @Inject
    private SessionService sessionService;
    
    @Inject
    private ApplicationConfig applicationConfig;
    
    @Inject
    private ServerConfig serverConfig;
    
    @Override
    public void loginUser(User dto, HttpServletResponse response) {
        UserEntity user = this.userService.validateLogin(dto);
        LoginSession session = sessionService.startSession(request.getRemoteAddr(), user.getId());
        Cookie cookie = createAuthorizationCookie(session.getSessionId());
        response.addCookie(cookie);
    }
    
    private Cookie createAuthorizationCookie(String sessionId) {
        Cookie cookie = new Cookie(SecurityConstants.COOKIE_NAME, sessionId);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(applicationConfig.getSessionDuration());
        cookie.setPath("/");
        if (serverConfig.getEnv().equals(Environments.PROD) && serverConfig.isProd()) {
            cookie.setSecure(true);
        }
        return cookie;
    }
    
    @Override
    public void logoutUser(HttpServletResponse response) {
        Cookie authorizationCookie = getAuthorizationCookie();
        if (authorizationCookie != null) {
            sessionService.removeSession(authorizationCookie.getValue());
            Cookie cookie = new Cookie(SecurityConstants.COOKIE_NAME, "LOGOUT");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
    
    @Override
    public void refreshLogin(String sessionId) {
        LoginSession session = sessionService.updateSession(sessionId);
        if (session != null) {
            createAuthorizationCookie(session.getSessionId());
        }
    }
    
    @Override
    public void validateAuthorization(boolean refreshToken) {
        String ip = request.getRemoteAddr();
    
        Cookie cookie = getAuthorizationCookie();
        if (cookie == null) {
            throw new BadLoginException();
        }
        
        String sessionId = cookie.getValue();
        LoginSession session = sessionService.getSession(sessionId);
        if (session == null) {
            throw new BadLoginException();
        }
    
        if (!ip.equals(session.getIp())) {
            throw new BadLoginException();
        }
    
        /*if (refreshToken) {
            refreshLogin(session.getSessionId());
        }*/
    }
    
    @Override
    public User getCurrentUser() {
        Cookie cookie = getAuthorizationCookie();
        if (cookie == null) {
            return null;
        }
        LoginSession session = sessionService.getSession(cookie.getValue());
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
            if (cookie.getName().equals(SecurityConstants.COOKIE_NAME)) {
                return cookie;
            }
        }
        return null;
    }
    
}
