package com.mjamsek.tasker.services.impl;

import com.mjamsek.tasker.lib.v1.exceptions.ValidationException;
import com.mjamsek.tasker.services.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class ValidatorImpl implements Validator {
    
    @Override
    public void assertNotNull(Object value) throws ValidationException {
        if (value == null) {
            throw new ValidationException("validation.error.property.null")
                .withDescription("Object could not be serialized due to missing values!")
                .withStatus(Response.Status.BAD_REQUEST.getStatusCode());
        }
    }
    
    @Override
    public void assertNotNull(Object value, String fieldName) throws ValidationException {
        if (value == null) {
            throw new ValidationException("validation.error.property.null")
                .withDescription(String.format("Object could not be serialized! Field '%s' must not be null!", fieldName))
                .withField(fieldName)
                .withStatus(Response.Status.BAD_REQUEST.getStatusCode());
        }
    }
    
    @Override
    public void assertNotNull(Object value, String fieldName, String entity) throws ValidationException {
        if (value == null) {
            throw new ValidationException("validation.error.property.null")
                .withDescription(String.format("Object could not be serialized! Field '%s' of entity '%s' must not be null!", fieldName, entity))
                .withField(fieldName)
                .withEntity(entity)
                .withStatus(Response.Status.BAD_REQUEST.getStatusCode());
        }
    }
    
    @Override
    public void assertNotBlank(String value) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("validation.error.property.null")
                .withDescription("Object could not be serialized due to missing values!")
                .withStatus(Response.Status.BAD_REQUEST.getStatusCode());
        }
    }
    
    @Override
    public void assertNotBlank(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("validation.error.property.null")
                .withDescription(String.format("Object could not be serialized! Field '%s' must not be null!", fieldName))
                .withField(fieldName)
                .withStatus(Response.Status.BAD_REQUEST.getStatusCode());
        }
    }
    
    @Override
    public void assertNotBlank(String value, String fieldName, String entity) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("validation.error.property.null")
                .withDescription(String.format("Object could not be serialized! Field '%s' of entity '%s' must not be null!", fieldName, entity))
                .withField(fieldName)
                .withEntity(entity)
                .withStatus(Response.Status.BAD_REQUEST.getStatusCode());
        }
    }
}
