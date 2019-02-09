package com.mjamsek.tasker.delegator.exceptions;

public class InvalidActionException extends DelegatorException {
    
    public InvalidActionException() {
        super("Invalid action! Given name doesn't match any task or plugin actions.");
    }
}
