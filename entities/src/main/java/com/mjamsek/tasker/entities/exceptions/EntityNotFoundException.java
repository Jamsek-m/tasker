package com.mjamsek.tasker.entities.exceptions;

public class EntityNotFoundException extends TaskerException {
    public EntityNotFoundException() {
        super();
    }
    
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
