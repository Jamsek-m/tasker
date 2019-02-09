package com.mjamsek.tasker.delegator.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AvailableActions {
    
    private List<String> tasks = new ArrayList<>();
    
    private HashMap<String, List<String>> plugins = new HashMap<>();
    
    public List<String> getTasks() {
        return tasks;
    }
    
    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }
    
    public HashMap<String, List<String>> getPlugins() {
        return plugins;
    }
    
    public void setPlugins(HashMap<String, List<String>> plugins) {
        this.plugins = plugins;
    }
}
