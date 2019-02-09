package com.mjamsek.tasker.admin.entities.exceptions;

public class BadLoginException extends AdminException {
    
    public BadLoginException() {
        super("Wrong username or password!");
    }
}
