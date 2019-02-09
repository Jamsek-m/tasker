package com.mjamsek.tasker.plugins.docker;

import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.annotations.Plugin;
import com.mjamsek.tasker.lib.plugins.TaskerPlugin;
import com.mjamsek.tasker.plugins.docker.actions.*;

import java.util.HashMap;
import java.util.Map;

@Plugin(name = "DockerPlugin", description = "Plugin for manipulating docker daemon.")
public class DockerPlugin implements TaskerPlugin {
    
    @Override
    public Map<String, Class<? extends Action>> getPluginActions() {
        Map<String, Class<? extends Action>> actions = new HashMap<>();
        actions.put("start", StartContainerAction.class);
        actions.put("stop", StopContainerAction.class);
        actions.put("getInfo", GetContainerInfoAction.class);
        actions.put("getId", GetContainerIdAction.class);
        actions.put("delete", DeleteContainerAction.class);
        actions.put("create", CreateContainerAction.class);
        actions.put("recreate", RecreateContainerAction.class);
        return actions;
    }
}
