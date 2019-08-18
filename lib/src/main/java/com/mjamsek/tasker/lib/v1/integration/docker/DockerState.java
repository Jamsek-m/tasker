package com.mjamsek.tasker.lib.v1.integration.docker;

public class DockerState {
    
    private String status;
    
    private boolean running;
    
    private boolean restarting;
    
    private boolean dead;
    
    private boolean paused;
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public boolean isRestarting() {
        return restarting;
    }
    
    public void setRestarting(boolean restarting) {
        this.restarting = restarting;
    }
    
    public boolean isDead() {
        return dead;
    }
    
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
