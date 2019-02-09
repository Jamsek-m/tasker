package com.mjamsek.tasker.lib.plugins;

import com.mjamsek.tasker.lib.action.Action;

import java.util.HashMap;
import java.util.Map;

public interface TaskerPlugin {
    
    default Map<String, Class<? extends Action>> getPluginActions() {
        return new HashMap<>();
    }
    
    static TaskerPlugin getInstance(Class<? extends TaskerPlugin> pluginClass) {
        try {
            return pluginClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
