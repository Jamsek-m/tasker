package com.mjamsek.tasker.services;

import com.mjamsek.tasker.entities.dto.User;
import com.mjamsek.tasker.entities.persistence.auth.LoginSession;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    
    void loginUser(User user, HttpServletResponse response);
    
    void logoutUser(HttpServletResponse response);
    
    void refreshLogin(String sessionId);
    
    void validateAuthorization(boolean refreshToken);
    
    User getCurrentUser();
    
}
