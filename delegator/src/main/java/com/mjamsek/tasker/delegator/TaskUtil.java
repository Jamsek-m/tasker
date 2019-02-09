package com.mjamsek.tasker.delegator;

import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.tasks.api.ApiCallTask;
import com.mjamsek.tasker.tasks.shell.ShellCmdTask;

import java.util.HashSet;
import java.util.Set;

public class TaskUtil {
    
    public static Set<Class<? extends Action>> loadTasks() {
        Set<Class<? extends Action>> tasks = new HashSet<>();
        
        tasks.add(ApiCallTask.class);
        tasks.add(ShellCmdTask.class);
        
        return tasks;
    }
    
}
