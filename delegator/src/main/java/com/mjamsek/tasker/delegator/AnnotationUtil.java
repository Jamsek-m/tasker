package com.mjamsek.tasker.delegator;

import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.annotations.Plugin;
import com.mjamsek.tasker.lib.annotations.Task;
import com.mjamsek.tasker.lib.plugins.TaskerPlugin;

public class AnnotationUtil {
    
    public static String getTaskName(Class<? extends Action> taskClass) {
        Task task = taskClass.getAnnotation(Task.class);
        if (task == null) {
            throw new RuntimeException("Missing annotation on task!");
        }
        return task.name();
    }
    
    public static String getPluginName(Class<? extends TaskerPlugin> pluginClass) {
        Plugin plugin = pluginClass.getAnnotation(Plugin.class);
        if (plugin == null) {
            throw new RuntimeException("Missing annotation on plugin!");
        }
        return plugin.name();
    }
}
