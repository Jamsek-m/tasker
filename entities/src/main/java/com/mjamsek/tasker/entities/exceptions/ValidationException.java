package com.mjamsek.tasker.entities.exceptions;

import com.mjamsek.tasker.lib.v1.exceptions.TaskerException;

public class ValidationException extends TaskerException {
    
    public ValidationException() {
        super();
    }
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
