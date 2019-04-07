package com.mjamsek.tasker;

import com.kumuluz.ee.cors.annotations.CrossOrigin;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("v2")
@CrossOrigin(
    allowOrigin = "*",
    supportedMethods = "GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD",
    exposedHeaders = "X-Total-Count, Access-Control-Allow-Credentials"
    // supportedHeaders = "X-Tasker-Key, X-Tasker-Name, Access-Control-Allow-Credentials"
)
public class TaskerApplication extends Application {
}
