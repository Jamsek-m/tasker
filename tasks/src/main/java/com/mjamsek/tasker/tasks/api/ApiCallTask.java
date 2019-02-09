package com.mjamsek.tasker.tasks.api;

import com.mjamsek.tasker.lib.action.Action;
import com.mjamsek.tasker.lib.action.ActionValidationException;
import com.mjamsek.tasker.lib.annotations.Task;
import com.mjamsek.tasker.lib.models.ActionInstruction;
import com.mjamsek.tasker.lib.models.ActionResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Task(name = "apiCall", description = "Calls specified api. Can act as proxy.")
public class ApiCallTask implements Action {

    @Override
    public ActionResponse executeAction(ActionInstruction actionInstruction) {
        this.validateInput(actionInstruction.getParameters());
        
        Client client = ClientBuilder.newClient();
        String url = (String) actionInstruction.getParameters().get("url");
        String method = (String) actionInstruction.getParameters().get("method");
    
        Invocation.Builder builder = client.target(url).request();
        
        if (actionInstruction.getParameters().containsKey("headers")) {
            Map<String, String> headers = (Map<String, String>) actionInstruction.getParameters().get("headers");
            for(String key : headers.keySet()) {
                builder = builder.header(key, headers.get(key));
            }
        }
        Invocation invocation;
        if (actionInstruction.getParameters().containsKey("body")) {
            Object body = actionInstruction.getParameters().get("body");
            invocation = builder.build(method, (Entity<?>) body);
        } else {
            invocation = builder.build(method);
        }
        Response response = invocation.invoke();
        
        ActionResponse actionResponse = new ActionResponse(actionInstruction.getActionName(), "SUCCESS");
        String stringifiedResponse = response.readEntity(String.class);
        ApiResponse apiResponse = new ApiResponse(response.getStatus(), stringifiedResponse);
        actionResponse.setActionResponse(apiResponse);
        return actionResponse;
    }
    
    private void validateInput(HashMap<String, Object> input) {
        if (!input.containsKey("url")) {
            throw new ActionValidationException("Missing url!", "apiCall");
        }
        if (!input.containsKey("method")) {
            throw new ActionValidationException("Missing method!", "apiCall");
        }
    }
    
}
