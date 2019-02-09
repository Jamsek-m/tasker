package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.admin.entities.exceptions.AdminException;
import com.mjamsek.tasker.admin.entities.exceptions.BadLoginException;
import com.mjamsek.tasker.admin.entities.exceptions.TokenExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

@Provider
public class AdminExceptionMapper implements ExceptionMapper<AdminException> {
    
    @Override
    public Response toResponse(AdminException exception) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());
        if (exception instanceof TokenExistsException) {
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        } else if (exception instanceof BadLoginException) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }
}
