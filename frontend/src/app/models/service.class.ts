import {DockerDaemon} from "./docker-daemon";

export class ServiceHealthCheck {
    public id: number;
    public healthUrl: string;
    public fixes: any[];
}

export class ServiceUrl {
    public id: number;
    public url: string;
    public version: string;
    public urlVersioning: boolean;
}

export class ServiceDeployment {
    public id: number;
    public containerId: string;
    public containerName: string;
    public version: string;
    public dockerDaemon: DockerDaemon;
}

export class Service {
    public id: number;
    public name: string;
    public version: string;
    public description: string;
    public serviceUrl: ServiceUrl;
    public deployment: ServiceDeployment;
    public healthCheck: ServiceHealthCheck;

    public static empty(): Service {
        const service = new Service();
        service.serviceUrl = new ServiceUrl();
        service.healthCheck = new ServiceHealthCheck();
        service.deployment = new ServiceDeployment();
        service.deployment.dockerDaemon = new DockerDaemon();
        service.healthCheck.fixes = [];
        return service;
    }
}
