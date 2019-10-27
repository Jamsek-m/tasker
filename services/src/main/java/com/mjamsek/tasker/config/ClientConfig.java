package com.mjamsek.tasker.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("client.keycloak")
public class ClientConfig {
    
    @ConfigValue("realm")
    private String realm;
    
    @ConfigValue("client-id")
    private String clientId;
    
    @ConfigValue("auth-url")
    private String authUrl;
    
    @ConfigValue("config-dir")
    private String configDir;
    
    public String getRealm() {
        return realm;
    }
    
    public void setRealm(String realm) {
        this.realm = realm;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getAuthUrl() {
        return authUrl;
    }
    
    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }
    
    public String getConfigDir() {
        return configDir;
    }
    
    public void setConfigDir(String configDir) {
        this.configDir = configDir;
    }
}
