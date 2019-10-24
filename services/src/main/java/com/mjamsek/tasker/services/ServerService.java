package com.mjamsek.tasker.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.lib.v1.Server;
import com.mjamsek.tasker.lib.v1.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.lib.v1.exceptions.PersistenceException;

import java.util.List;

public interface ServerService {
    
    List<Server> getServers(QueryParameters queryParameters);
    
    long getServersCount(QueryParameters queryParameters);
    
    Server getServer(String serverId) throws EntityNotFoundException;
    
    Server createServer(Server server) throws PersistenceException;
    
    Server updateServer(Server server, String serverId) throws PersistenceException;
    
    void deleteServer(String serverId) throws PersistenceException;
}
