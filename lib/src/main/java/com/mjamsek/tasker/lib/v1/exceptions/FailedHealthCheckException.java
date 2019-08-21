package com.mjamsek.tasker.lib.v1.exceptions;

public class FailedHealthCheckException extends TaskerException {
    
    public FailedHealthCheckException(String serviceName) {
        super("'" + serviceName +"' has bad health!");
    }
}
