package com.mjamsek.tasker.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.mjamsek.tasker.config.ClientConfig;
import com.mjamsek.tasker.config.ServerConfig;
import com.mjamsek.tasker.services.StartupService;
import org.h2.tools.RunScript;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

@ApplicationScoped
public class StartupServiceImpl implements StartupService {
    
    private static final Logger LOG = LogManager.getLogger(StartupServiceImpl.class.getName());
    
    private static final String doneFileName = "data/done.txt";
    private static final String structureFileName = "sql/structure.sql";
    private static final String dataFileName = "sql/data.sql";
    
    @Inject
    private ServerConfig serverConfig;
    
    @Inject
    private ClientConfig clientConfig;
    
    @Resource(mappedName = "jdbc/mainDB")
    private DataSource dataSource;
    
    @Override
    public void startupServer() {
        if (!this.serviceWasInitialized()) {
            
            this.createDatabaseStructure();
            this.populateData();
            this.createKeycloakConfigFile();
            LOG.info("Tasker service was initialized!");
            this.writeInitFile();
        }
    }
    
    private boolean serviceWasInitialized() {
        return Files.exists(Paths.get(doneFileName));
    }
    
    private void createDatabaseStructure() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(structureFileName);
            if (is == null) {
                
                System.exit(1);
            }
            RunScript.execute(dataSource.getConnection(), new InputStreamReader(is));
            LOG.info("Tasker DB structure is initialized!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private void populateData() {
        // Add default configuration values
        /*try (Connection conn = dataSource.getConnection()) {
            
            PreparedStatement createConfigStatement = conn
                .prepareStatement("INSERT INTO configuration(id, config_key, config_value) VALUES (?, ?, ?)");
            createConfigStatement.setString(1, "795b4157-5714-4856-ad9c-448985cf5023");
            createConfigStatement.setString(2, "TASKER_ENABLED_REGISTRATION");
            createConfigStatement.setString(3, "false");
            createConfigStatement.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }*/
    }
    
    private void writeInitFile() {
        try {
            FileWriter fileWriter = new FileWriter(new File(doneFileName));
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private void createKeycloakConfigFile() {
        
        ObjectMapper mapper = new ObjectMapper();
        
        ObjectNode keycloakNode = mapper.createObjectNode();
        keycloakNode.put("realm", clientConfig.getRealm());
        keycloakNode.put("auth-server-url", clientConfig.getAuthUrl());
        keycloakNode.put("resource", clientConfig.getClientId());
        
        ObjectNode configNode = mapper.createObjectNode();
        configNode.set("keycloak", keycloakNode);
    
        try {
            
            Files.createDirectories(Paths.get(clientConfig.getConfigDir()));
            
            String clientJsonConfig = mapper.writeValueAsString(configNode);
            FileWriter fileWriter = new FileWriter(new File(Paths.get(clientConfig.getConfigDir(), "config.json").toString()));
            fileWriter.write(clientJsonConfig);
            fileWriter.close();
    
            LOG.info("Tasker client configuration is created!");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
