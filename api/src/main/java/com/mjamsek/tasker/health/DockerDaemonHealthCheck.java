package com.mjamsek.tasker.health;

import com.mjamsek.tasker.services.DockerService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Readiness
@ApplicationScoped
public class DockerDaemonHealthCheck implements HealthCheck {
    
    private static final String HEALTHCHECK_NAME = "DockerDaemonsHealthCheck";
    
    @Inject
    private DockerService dockerService;
    
    @Override
    public HealthCheckResponse call() {
        try {
            dockerService.checkEndpointAvailability();
        } catch (Exception exc) {
            return HealthCheckResponse.named(HEALTHCHECK_NAME).down().build();
        }
        return HealthCheckResponse.named(HEALTHCHECK_NAME).up().build();
    }
}
