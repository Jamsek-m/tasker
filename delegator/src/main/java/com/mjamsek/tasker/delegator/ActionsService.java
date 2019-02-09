package com.mjamsek.tasker.delegator;

import com.mjamsek.tasker.delegator.models.AvailableActions;
import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.plugins.TaskerPlugin;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ActionsService {
    
    public AvailableActions getAllActions() {
        AvailableActions actions = new AvailableActions();
        
        List<String> tasks = TaskUtil.loadTasks().stream()
            .map(AnnotationUtil::getTaskName)
            .collect(Collectors.toList());
        actions.getTasks().addAll(tasks);
        
        for(Class<? extends TaskerPlugin> plugin : PluginUtil.loadPlugins()) {
            String pluginName = AnnotationUtil.getPluginName(plugin);
    
            TaskerPlugin pluginInstance = createInstance(plugin);
            if (pluginInstance != null) {
                Map<String, Class<? extends Action>> pluginActions = pluginInstance.getPluginActions();
                actions.getPlugins().put(pluginName, new ArrayList<>(pluginActions.keySet()));
            }
        }
        return actions;
    }
    
    private TaskerPlugin createInstance(Class<? extends TaskerPlugin> pluginClass) {
        try {
            return pluginClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
