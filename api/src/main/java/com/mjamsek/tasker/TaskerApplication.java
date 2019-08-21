package com.mjamsek.tasker;

import com.mjamsek.tasker.lib.v1.common.AuthRole;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/v1")
@DeclareRoles({AuthRole.ADMIN, AuthRole.DEVELOPER, AuthRole.USER})
public class TaskerApplication extends Application {

}
