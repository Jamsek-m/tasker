package com.mjamsek.tasker.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.entities.persistence.service.DockerDaemon;

import java.util.List;

public interface DockerDaemonService {
    
    List<DockerDaemon> getDaemons(QueryParameters queryParameters);
    
    long getDaemonsCount(QueryParameters queryParameters);
    
    DockerDaemon getDaemon(long daemonId);
    
    DockerDaemon getDaemon(String deamonName);
    
    DockerDaemon saveDaemon(DockerDaemon daemon);
    
    DockerDaemon updateDaemon(DockerDaemon daemon, long daemonId);
    
    void deleteDaemon(long daemonId);
}
