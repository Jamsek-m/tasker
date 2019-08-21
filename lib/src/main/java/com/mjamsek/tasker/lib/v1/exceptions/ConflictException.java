package com.mjamsek.tasker.lib.v1.exceptions;

public class ConflictException extends TaskerException {
    public ConflictException() {
        super();
    }
    
    public ConflictException(String message) {
        super(message);
    }
    
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ConflictException(Throwable cause) {
        super(cause);
    }
}
