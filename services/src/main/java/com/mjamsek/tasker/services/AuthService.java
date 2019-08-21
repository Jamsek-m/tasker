package com.mjamsek.tasker.services;

import org.keycloak.representations.AccessToken;

public interface AuthService {
    
    AccessToken getAccessToken();
    
}
