package com.mjamsek.tasker.lib.v1.exceptions;

public class BadLoginException extends UnauthorizedException {
    
    public BadLoginException() {
        super("Wrong username or password!");
    }
}
