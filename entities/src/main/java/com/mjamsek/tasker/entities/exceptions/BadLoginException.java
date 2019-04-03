package com.mjamsek.tasker.entities.exceptions;

public class BadLoginException extends AdminException {
    
    public BadLoginException() {
        super("Wrong username or password!");
    }
}
