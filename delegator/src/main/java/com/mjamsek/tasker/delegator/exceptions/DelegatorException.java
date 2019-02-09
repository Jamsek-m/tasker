package com.mjamsek.tasker.delegator.exceptions;

public class DelegatorException extends RuntimeException {
    
    public DelegatorException() {
    }
    
    public DelegatorException(String message) {
        super(message);
    }
    
    public DelegatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
