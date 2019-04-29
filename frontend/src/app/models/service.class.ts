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
}

export class ServiceDeployment {
    public id: number;
    public containerId: string;
    public containerName: string;
    public version: string;
    public dockerDaemon: DockerDaemon;

    constructor() {
        this.dockerDaemon = new DockerDaemon();
    }
}

export class Service {
    public id: number;
    public name: string;
    public description: string;
    public serviceUrl: ServiceUrl;
    public deployment: ServiceDeployment;
    public healthCheck: ServiceHealthCheck;

    public static empty(): Service {
        const service = new Service();
        service.serviceUrl = new ServiceUrl();
        service.healthCheck = new ServiceHealthCheck();
        service.deployment = new ServiceDeployment();
        service.healthCheck.fixes = [];
        return service;
    }
}
