package com.mjamsek.tasker.lib.v1.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mjamsek.tasker.lib.v1.common.HttpStatus;

public class ValidationException extends RuntimeException {
    
    private ValidationExceptionResponse response;
    
    public ValidationException(String code) {
        this.response = new ValidationExceptionResponse();
        this.response.code = code;
    }
    
    public ValidationException withStatus(int status) {
        this.response.status = status;
        return this;
    }
    
    public ValidationException withField(String field) {
        this.response.field = field;
        return this;
    }
    
    public ValidationException withEntity(String entity) {
        this.response.entity = entity;
        return this;
    }
    
    public ValidationException withDescription(String description) {
        this.response.description = description;
        return this;
    }
    
    public ValidationException withMoreInfo(String moreInfo) {
        this.response.moreInfo = moreInfo;
        return this;
    }
    
    public ValidationException isValidationError() {
        this.response.status = HttpStatus.VALIDATION_FAILED;
        return this;
    }
    
    public ValidationException isBadRequest() {
        this.response.status = HttpStatus.BAD_REQUEST;
        return this;
    }
    
    public ValidationExceptionResponse getResponse() {
        return this.response;
    }
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ValidationExceptionResponse {
        
        private int status;
        
        private String code;
        
        private String field;
        
        private String entity;
        
        private String description;
        
        private String moreInfo;
    
        public int getStatus() {
            return status;
        }
    
        public void setStatus(int status) {
            this.status = status;
        }
    
        public String getCode() {
            return code;
        }
    
        public void setCode(String code) {
            this.code = code;
        }
    
        public String getField() {
            return field;
        }
    
        public void setField(String field) {
            this.field = field;
        }
    
        public String getEntity() {
            return entity;
        }
    
        public void setEntity(String entity) {
            this.entity = entity;
        }
    
        public String getDescription() {
            return description;
        }
    
        public void setDescription(String description) {
            this.description = description;
        }
    
        public String getMoreInfo() {
            return moreInfo;
        }
    
        public void setMoreInfo(String moreInfo) {
            this.moreInfo = moreInfo;
        }
    }
}
