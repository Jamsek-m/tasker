package com.mjamsek.tasker.services;

import com.mjamsek.tasker.entities.persistence.auth.LoginSession;

public interface SessionService {
    
    void deleteAllOtherSessions(String ip);
    
    LoginSession startSession(String ip, long userId);
    
    LoginSession getSessionByIp(String ip);
    
    LoginSession getSession(String sessionId);
    
    void removeSession(String sessionId);
    
    LoginSession updateSession(String sessionId);
}
