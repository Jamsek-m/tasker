package com.mjamsek.tasker.lib.v1.integration.docker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerLogConfig {
    @JsonProperty("Type")
    public String type;
}
