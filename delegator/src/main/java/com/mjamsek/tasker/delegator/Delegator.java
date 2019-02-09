package com.mjamsek.tasker.delegator;

import com.mjamsek.tasker.delegator.exceptions.DelegatorException;
import com.mjamsek.tasker.delegator.exceptions.InvalidActionException;
import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;
import com.mjamsek.tasker.lib.plugins.TaskerPlugin;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class Delegator {
    
    private Map<String, Action> actionsCache;
    
    @PostConstruct
    private void init() {
        this.actionsCache = new HashMap<>();
    }
    
    public ActionResponse delegateAction(ActionInstruction actionInstruction) {
        Action action = getInstance(actionInstruction);
        return action.executeAction(actionInstruction);
    }
    
    private Action getInstance(ActionInstruction actionInstruction) {
        if (actionsCache.containsKey(actionInstruction.getActionName())) {
            return actionsCache.get(actionInstruction.getActionName());
        }
    
        Class<? extends Action> actionClass = this.matchTask(actionInstruction);
        if (actionClass == null) {
            actionClass = this.matchPluginAction(actionInstruction);
        }
        
        if (actionClass == null) {
            throw new InvalidActionException();
        }
        
        Action action = this.createInstance(actionClass);
        if (action == null) {
            throw new DelegatorException("Error creating instance of " + actionClass.getSimpleName());
        }
        actionsCache.put(actionInstruction.getActionName(), action);
        return action;
    }
    
    private Class<? extends Action> matchTask(ActionInstruction actionInstruction) {
        Set<Class<? extends Action>> tasks = TaskUtil.loadTasks();
        for(Class<? extends Action> task : tasks) {
            if (AnnotationUtil.getTaskName(task).equals(actionInstruction.getActionName())) {
                return task;
            }
        }
        return null;
    }
    
    private Class<? extends Action> matchPluginAction(ActionInstruction actionInstruction) {
        String[] name = actionInstruction.getActionName().split("\\.");
        if (name.length != 2) {
            return null;
        }
        String pluginName = name[0];
        String actionName = name[1];
        
        Set<Class<? extends TaskerPlugin>> plugins = PluginUtil.loadPlugins();
        for(Class<? extends TaskerPlugin> plugin : plugins) {
            if (AnnotationUtil.getPluginName(plugin).equals(pluginName)) {
                TaskerPlugin pluginInstance = TaskerPlugin.getInstance(plugin);
                if (pluginInstance != null && pluginInstance.getPluginActions().containsKey(actionName)) {
                    return pluginInstance.getPluginActions().get(actionName);
                }
            }
        }
        return null;
    }
    
    private Action createInstance(Class<? extends Action> actionClass) {
        try {
            return actionClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
