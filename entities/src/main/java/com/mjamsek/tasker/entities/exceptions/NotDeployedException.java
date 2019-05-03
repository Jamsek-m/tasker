package com.mjamsek.tasker.entities.exceptions;

public class NotDeployedException extends TaskerException {
    
    public NotDeployedException() {
        super();
    }
    
    public NotDeployedException(String message) {
        super(message);
    }
    
    public NotDeployedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NotDeployedException(Throwable cause) {
        super(cause);
    }
}
