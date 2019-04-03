package com.mjamsek.tasker.auth;

import com.mjamsek.tasker.services.AuthService;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@SecureResource
public class SecureResourceInterceptor {
    
    @Inject
    private AuthService authService;
    
    @AroundInvoke
    public Object checkCredentials(InvocationContext context) throws Exception {
        
        NoAuthRefresh noAuth = context.getMethod().getAnnotation(NoAuthRefresh.class);
        if (noAuth == null) {
            // refresh token
            authService.validateAuthorization(true);
        } else {
            authService.validateAuthorization(false);
        }
        
        return context.proceed();
    }
}
