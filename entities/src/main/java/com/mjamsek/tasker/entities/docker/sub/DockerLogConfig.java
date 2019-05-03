package com.mjamsek.tasker.entities.docker.sub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerLogConfig {
    @JsonProperty("Type")
    public String type;
}
