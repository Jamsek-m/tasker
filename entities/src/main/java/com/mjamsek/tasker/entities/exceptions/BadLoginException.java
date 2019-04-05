package com.mjamsek.tasker.entities.exceptions;

public class BadLoginException extends UnauthorizedException {
    
    public BadLoginException() {
        super("Wrong username or password!");
    }
}
