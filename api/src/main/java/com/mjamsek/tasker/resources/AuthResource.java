package com.mjamsek.tasker.resources;

import com.mjamsek.tasker.auth.NoAuthRefresh;
import com.mjamsek.tasker.auth.SecureResource;
import com.mjamsek.tasker.entities.dto.User;
import com.mjamsek.tasker.entities.persistence.admin.ConfigEntry;
import com.mjamsek.tasker.entities.persistence.admin.LogSeverity;
import com.mjamsek.tasker.http.HttpHeader;
import com.mjamsek.tasker.services.AuthService;
import com.mjamsek.tasker.services.ConfigService;
import com.mjamsek.tasker.services.LogService;
import com.mjamsek.tasker.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    
    @Inject
    private UserService userService;
    
    @Inject
    private AuthService authService;
    
    @Inject
    private ConfigService configService;
    
    @Inject
    private LogService logService;
    
    @POST
    @Path("/login")
    @NoAuthRefresh
    public Response login(User dto, @Context HttpServletResponse response) {
        authService.loginUser(dto, response);
        return Response.ok().header(HttpHeader.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true").build();
    }
    
    @GET
    @Path("/logout")
    @NoAuthRefresh
    public Response logout(@Context HttpServletResponse response) {
        authService.logoutUser(response);
        return Response.noContent().header(HttpHeader.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true").build();
    }
    
    @GET
    @Path("/is-authorized")
    @SecureResource
    @NoAuthRefresh
    public Response isAuthorized() {
        return Response.ok().header(HttpHeader.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true").build();
    }
    
    @GET
    @Path("/current-user")
    @SecureResource
    public Response getCurrentUser() {
        return Response.ok(authService.getCurrentUser()).build();
    }
    
    @PUT
    @Path("/user/{id}")
    @SecureResource
    public Response changePassword(@PathParam("id") long userId, User dto) {
        userService.changePassword(dto, userId);
        logService.log(LogSeverity.INFO, "User with id: " + userId + " has changed password.");
        return Response.ok().build();
    }
    
    @POST
    @Path("/new-user")
    public Response createNewUser(User dto) {
        ConfigEntry config = configService.getConfig("TASKER_ENABLED_REGISTRATION");
        boolean enabled = Boolean.valueOf(config.getValue());
        if (enabled) {
            boolean created = userService.createUser(dto);
            if (created) {
                logService.log(LogSeverity.INFO, "User '" + dto.getUsername() + "' has been registered.");
                return Response.status(Response.Status.CREATED).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
    
}
