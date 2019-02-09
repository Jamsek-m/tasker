package com.mjamsek.tasker.plugins.docker.models.list;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class DockerContainerCard {
    
    private String id;
    
    private String name;
    
    private String image;
    
    public static DockerContainerCard fromJsonObject(JsonObject json) {
        DockerContainerCard container = new DockerContainerCard();
        container.setId(json.getString("Id"));
        container.setImage(json.getString("Image"));
        JsonArray namesArray = json.getJsonArray("Names");
        
        if (namesArray.size() > 0) {
            container.setName(namesArray.getString(0));
        } else {
            container.setName("");
        }
        
        return container;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
}
