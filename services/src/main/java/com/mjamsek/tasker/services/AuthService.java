package com.mjamsek.tasker.services;

import com.mjamsek.tasker.entities.dto.User;
import com.mjamsek.tasker.entities.persistence.LoginSession;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    
    void loginUser(User user, HttpServletResponse response);
    
    void deleteAllOtherSessions(String ip);
    
    LoginSession startSession(User user);
    
    LoginSession getSession(String ip);
    
    void removeLoginSession(String ip);
    
    void logoutUser(HttpServletResponse response);
    
    void refreshLogin();
    
    void validateAuthorization(boolean refreshToken);
    
    User getCurrentUser();
    
}
