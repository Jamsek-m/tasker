package com.mjamsek.tasker.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("kumuluzee")
public class ServerConfig {
    
    private String name;
    
    private String version;
    
    @ConfigValue("env.name")
    private String env;
    
    @ConfigValue("env.prod")
    private Boolean prod;
    
    @ConfigValue("health.servlet.mapping")
    private String healthUrl;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getEnv() {
        return env;
    }
    
    public void setEnv(String env) {
        this.env = env;
    }
    
    public boolean isProd() {
        return prod;
    }
    
    public void setProd(boolean prod) {
        this.prod = prod;
    }
    
    public String getHealthUrl() {
        return healthUrl;
    }
    
    public void setHealthUrl(String healthUrl) {
        this.healthUrl = healthUrl;
    }
}
