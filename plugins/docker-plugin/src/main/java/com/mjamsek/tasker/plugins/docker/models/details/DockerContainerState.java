package com.mjamsek.tasker.plugins.docker.models.details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainerState {
    
    @JsonProperty("Status")
    private String Status;
    
    @JsonProperty("Running")
    private boolean Running;
    
    @JsonProperty("Paused")
    private boolean Paused;
    
    @JsonProperty("Restarting")
    private boolean Restarting;
    
    @JsonProperty("Dead")
    private boolean Dead;
    
    @JsonProperty("ExitCode")
    private int ExitCode;
    
    @JsonProperty("Error")
    private String Error;
    
    @JsonProperty("StartedAt")
    private Date StartedAt;
    
    @JsonProperty("FinishedAt")
    private Date FinishedAt;
    
    public String getStatus() {
        return Status;
    }
    
    public void setStatus(String status) {
        Status = status;
    }
    
    public boolean isRunning() {
        return Running;
    }
    
    public void setRunning(boolean running) {
        Running = running;
    }
    
    public boolean isPaused() {
        return Paused;
    }
    
    public void setPaused(boolean paused) {
        Paused = paused;
    }
    
    public boolean isRestarting() {
        return Restarting;
    }
    
    public void setRestarting(boolean restarting) {
        Restarting = restarting;
    }
    
    public boolean isDead() {
        return Dead;
    }
    
    public void setDead(boolean dead) {
        Dead = dead;
    }
    
    public int getExitCode() {
        return ExitCode;
    }
    
    public void setExitCode(int exitCode) {
        ExitCode = exitCode;
    }
    
    public String getError() {
        return Error;
    }
    
    public void setError(String error) {
        Error = error;
    }
    
    public Date getStartedAt() {
        return StartedAt;
    }
    
    public void setStartedAt(Date startedAt) {
        StartedAt = startedAt;
    }
    
    public Date getFinishedAt() {
        return FinishedAt;
    }
    
    public void setFinishedAt(Date finishedAt) {
        FinishedAt = finishedAt;
    }
}
