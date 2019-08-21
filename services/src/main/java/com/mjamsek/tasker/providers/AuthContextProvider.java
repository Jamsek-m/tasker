package com.mjamsek.tasker.providers;

import com.mjamsek.tasker.services.AuthService;
import org.keycloak.representations.AccessToken;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@RequestScoped
public class AuthContextProvider {

    @Inject
    private AuthService authService;
    
    @Produces
    @RequestScoped
    public AuthContext produceAuthContext() {
        AccessToken token = authService.getAccessToken();
        if (token == null) return null;
        AuthContext context = new AuthContext();
        context.setId(token.getSubject());
        context.setUsername(token.getPreferredUsername());
        return context;
    }
    
}
