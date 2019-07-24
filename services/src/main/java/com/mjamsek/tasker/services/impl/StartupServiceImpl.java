package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.mjamsek.tasker.config.ServerConfig;
import com.mjamsek.tasker.services.StartupService;
import org.h2.tools.RunScript;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ApplicationScoped
public class StartupServiceImpl implements StartupService {
    
    private static final Logger LOG = LogManager.getLogger(StartupServiceImpl.class.getName());
    
    private static final String doneFileName = "init/done.txt";
    private static final String structureFileName = "sql/structure.sql";
    private static final String dataFileName = "sql/data.sql";
    
    @Inject
    private ServerConfig serverConfig;
    
    @Resource(mappedName = "jdbc/mainDB")
    private DataSource dataSource;
    
    @Override
    public void startupServer() {
        if (!this.serviceWasInitialized()) {
            
            this.createDatabaseStructure();
            this.populateData();
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
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private void populateData() {
        try (Connection conn = dataSource.getConnection()) {
            
            PreparedStatement createConfigStatement = conn
                .prepareStatement("INSERT INTO configuration(config_key, config_value) VALUES (?, ?)");
            createConfigStatement.setString(1, "TASKER_ENABLED_REGISTRATION");
            createConfigStatement.setString(2, "false");
            createConfigStatement.execute();
    
            PreparedStatement addAdminUserStatement = conn
                .prepareStatement("INSERT INTO users(password, username) VALUES (?, ?)");
            String password = BCrypt.hashpw(ConfigurationUtil.getInstance().get("tasker.admin.password").get(), BCrypt.gensalt());
            addAdminUserStatement.setString(1, password);
            addAdminUserStatement.setString(2, ConfigurationUtil.getInstance().get("tasker.admin.username").get());
            addAdminUserStatement.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
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
}
