package com.mjamsek.tasker.entities.exceptions;

public class FailedHealthCheckException extends TaskerException {
    
    public FailedHealthCheckException(String serviceName) {
        super("'" + serviceName +"' has bad health!");
    }
}
