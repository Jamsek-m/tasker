package com.mjamsek.tasker.delegator;

import com.mjamsek.tasker.lib.plugins.TaskerPlugin;
import com.mjamsek.tasker.plugins.docker.DockerPlugin;


import java.util.HashSet;
import java.util.Set;

public class PluginUtil {

    public static Set<Class<? extends TaskerPlugin>> loadPlugins() {
        Set<Class<? extends TaskerPlugin>> plugins = new HashSet<>();
        
        // Add new plugins here
        plugins.add(DockerPlugin.class);
        
        return plugins;
    }

}
