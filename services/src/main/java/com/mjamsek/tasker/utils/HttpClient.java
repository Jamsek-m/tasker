package com.mjamsek.tasker.utils;

import com.mjamsek.tasker.entities.enums.HttpMethod;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

public class HttpClient {
    
    public static Response exec(HttpMethod method, String url) {
        Client client = ClientBuilder.newClient();
        Invocation.Builder builder = client.target(url).request();
        Invocation invocation = builder.build(method.name());
        return invocation.invoke();
    }
    
    public static Response get(String url) {
        return exec(HttpMethod.GET, url);
    }
    
    public static Response post(String url) {
        return exec(HttpMethod.POST, url);
    }
}
