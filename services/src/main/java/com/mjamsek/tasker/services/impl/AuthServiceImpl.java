package com.mjamsek.tasker.services.impl;

import com.mjamsek.tasker.services.AuthService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequestScoped
public class AuthServiceImpl implements AuthService {
    
    @Inject
    private HttpServletRequest request;
    
    @Override
    public AccessToken getAccessToken() {
        Principal principal = request.getUserPrincipal();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
        if (keycloakPrincipal == null) return null;
        KeycloakSecurityContext ctx = keycloakPrincipal.getKeycloakSecurityContext();
        return ctx.getToken();
    }
    
}
