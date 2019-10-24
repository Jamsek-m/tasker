package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.entities.persistence.ServerEntity;
import com.mjamsek.tasker.lib.v1.Server;

public class ServerMapper {
    
    public static Server fromEntity(ServerEntity entity) {
        Server server = new Server();
        server.setId(entity.getId());
        server.setName(entity.getName());
        server.setIpAddress(entity.getIpAddress());
        return server;
    }
    
    public static ServerEntity toEntity(Server server) {
        ServerEntity entity = new ServerEntity();
        entity.setName(server.getName());
        entity.setIpAddress(server.getIpAddress());
        return entity;
    }
}
