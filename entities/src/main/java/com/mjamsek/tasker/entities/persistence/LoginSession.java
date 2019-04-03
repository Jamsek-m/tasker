package com.mjamsek.tasker.entities.persistence;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "login_sessions", indexes = @Index(name = "IP_UNIQUE_INDEX", columnList = "ip", unique = true))
@NamedQueries({
    @NamedQuery(name = LoginSession.FIND_BY_IP, query = "SELECT l FROM LoginSession l WHERE l.ip = :ip"),
    @NamedQuery(name = LoginSession.DELETE_BY_IP, query = "DELETE FROM LoginSession l WHERE l.ip = :ip")
})
public class LoginSession {
    
    public static final String FIND_BY_IP = "LoginSession.findByIp";
    public static final String DELETE_BY_IP = "LoginSession.deleteByIp";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String ip;
    
    private Date expires;
    
    @Column(name = "session_id", unique = true)
    private String sessionId;
    
    @Column(name = "user_id")
    private long userId;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public Date getExpires() {
        return expires;
    }
    
    public void setExpires(Date expires) {
        this.expires = expires;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public long getUserId() {
        return userId;
    }
    
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
