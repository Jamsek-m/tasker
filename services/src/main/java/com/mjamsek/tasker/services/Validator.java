package com.mjamsek.tasker.services;

import com.mjamsek.tasker.lib.v1.exceptions.ValidationException;

public interface Validator {
    
    void assertNotNull(Object value) throws ValidationException;
    
    void assertNotNull(Object value, String fieldName) throws ValidationException;
    
    void assertNotNull(Object value, String fieldName, String entity) throws ValidationException;
    
    void assertNotBlank(String value) throws ValidationException;
    
    void assertNotBlank(String value, String fieldName) throws ValidationException;
    
    void assertNotBlank(String value, String fieldName, String entity) throws ValidationException;
    
    void assertRegex(String value, String regex) throws ValidationException;
    
    void assertRegex(String value, String regex, String fieldName) throws ValidationException;
    
    void assertRegex(String value, String regex, String fieldName, String entity) throws ValidationException;
    
}
