package com.mjamsek.tasker.lib.v1.exceptions;

public class MissingHealthCheckException extends TaskerException {
    public MissingHealthCheckException() {
        super("No healthcheck defined");
    }
}
