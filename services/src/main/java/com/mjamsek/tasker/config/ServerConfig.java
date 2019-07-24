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
    
    @ConfigValue("datasources")
    private DatasourceConfig[] datasources;
    
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
    
    public Boolean getProd() {
        return prod;
    }
    
    public void setProd(Boolean prod) {
        this.prod = prod;
    }
    
    public DatasourceConfig[] getDatasources() {
        return datasources;
    }
    
    public void setDatasources(DatasourceConfig[] datasources) {
        this.datasources = datasources;
    }
    
    public static class DatasourceConfig {
        @ConfigValue("connection-url")
        private String connectionUrl;
        private String username;
        private String password;
        @ConfigValue("jndi-name")
        private String jndiName;
    
        public String getConnectionUrl() {
            return connectionUrl;
        }
    
        public void setConnectionUrl(String connectionUrl) {
            this.connectionUrl = connectionUrl;
        }
    
        public String getUsername() {
            return username;
        }
    
        public void setUsername(String username) {
            this.username = username;
        }
    
        public String getPassword() {
            return password;
        }
    
        public void setPassword(String password) {
            this.password = password;
        }
    
        public String getJndiName() {
            return jndiName;
        }
    
        public void setJndiName(String jndiName) {
            this.jndiName = jndiName;
        }
    }
}
