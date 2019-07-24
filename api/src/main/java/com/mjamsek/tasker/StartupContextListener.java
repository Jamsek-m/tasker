package com.mjamsek.tasker;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.mjamsek.tasker.services.StartupService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@ApplicationScoped
public class StartupContextListener implements ServletContextListener {
    
    private static final Logger LOG = LogManager.getLogger(StartupContextListener.class.getSimpleName());
    
    @Inject
    private StartupService startupService;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        startupService.startupServer();
        LOG.info("Tasker service has started successfully!");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    
    }
}
