package com.mjamsek.tasker.lib.v1.exceptions;

public class DockerException extends TaskerException {
    //TODO: add fields specifying error
    
    
    public DockerException() {
        super();
    }
    
    public DockerException(String message) {
        super(message);
    }
    
    public DockerException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DockerException(Throwable cause) {
        super(cause);
    }
}
