package com.mjamsek.tasker.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("tasker")
public class ApplicationConfig {
    
    @ConfigValue("session.duration")
    private int sessionDuration;
    
    public int getSessionDuration() {
        return sessionDuration;
    }
    
    public void setSessionDuration(int sessionDuration) {
        this.sessionDuration = sessionDuration;
    }
}
