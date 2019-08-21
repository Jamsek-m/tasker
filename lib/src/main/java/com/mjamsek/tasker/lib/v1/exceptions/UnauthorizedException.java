package com.mjamsek.tasker.lib.v1.exceptions;

public class UnauthorizedException extends TaskerException {
    
    public UnauthorizedException() {
        super();
    }
    
    public UnauthorizedException(String message) {
        super(message);
    }
    
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UnauthorizedException(Throwable cause) {
        super(cause);
    }
}
