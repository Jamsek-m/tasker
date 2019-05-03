package com.mjamsek.tasker.entities.docker.sub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerRestartPolicy {
    @JsonProperty("Name")
    public String name;
    @JsonProperty("MaximumRetryCount")
    public int maximumRetryCount;
}
