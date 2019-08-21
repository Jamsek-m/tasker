package com.mjamsek.tasker.lib.v1.exceptions;

public class DockerDaemonUnavailableException extends DockerException {
    
    public DockerDaemonUnavailableException() {
        super();
    }
    
    public DockerDaemonUnavailableException(String message) {
        super(message);
    }
    
    public DockerDaemonUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DockerDaemonUnavailableException(Throwable cause) {
        super(cause);
    }
}
