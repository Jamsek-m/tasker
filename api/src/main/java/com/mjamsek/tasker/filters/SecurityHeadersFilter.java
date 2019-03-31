package com.mjamsek.tasker.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityHeadersFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.addHeader("X-Content-Type-Options", "nosniff");
            resp.addHeader("X-XSS-Protection", "1; mode=block");
            resp.addHeader("X-Frame-Options", "DENY");
            resp.addHeader("Strict-Transport-Security", "max-age=63072000");
            chain.doFilter(request, resp);
        } else {
            chain.doFilter(request, response);
        }
    }
    
    @Override
    public void destroy() {
    
    }
}
