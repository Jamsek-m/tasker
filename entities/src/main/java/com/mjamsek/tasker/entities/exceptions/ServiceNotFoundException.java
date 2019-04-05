package com.mjamsek.tasker.entities.exceptions;

public class ServiceNotFoundException extends EntityNotFoundException {
    
    public ServiceNotFoundException(String serviceName) {
        super("Service with name '" + serviceName + "' not found!");
    }
    
    public ServiceNotFoundException(long serviceId) {
        super("Service with id '" + serviceId + "' not found!");
    }
}
