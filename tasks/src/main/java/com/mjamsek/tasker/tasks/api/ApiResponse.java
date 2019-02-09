package com.mjamsek.tasker.tasks.api;

class ApiResponse {
    private int status;
    private Object body;
    
    public ApiResponse() {}
    
    public ApiResponse(int status, Object body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
