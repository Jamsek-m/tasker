package com.mjamsek.tasker.entities.exceptions;

public class MissingHealthCheckException extends TaskerException {
    public MissingHealthCheckException() {
        super("No healthcheck defined");
    }
}
