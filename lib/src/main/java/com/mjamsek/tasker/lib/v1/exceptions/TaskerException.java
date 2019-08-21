package com.mjamsek.tasker.lib.v1.exceptions;

public class TaskerException extends RuntimeException {
    
    public TaskerException() {
    }
    
    public TaskerException(String message) {
        super(message);
    }
    
    public TaskerException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public TaskerException(Throwable cause) {
        super(cause);
    }
}
