package com.mjamsek.tasker.lib.v1.exceptions;

public class DockerContainerNotFoundException extends DockerException {
    public DockerContainerNotFoundException() {
        super();
    }
    
    public DockerContainerNotFoundException(String message) {
        super(message);
    }
    
    public DockerContainerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DockerContainerNotFoundException(Throwable cause) {
        super(cause);
    }
}
