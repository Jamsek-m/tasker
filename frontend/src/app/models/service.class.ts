
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

export class Service {
    public id: number;
    public name: string;
    public description: string;
    public serviceUrl: ServiceUrl;
    public healthCheck: ServiceHealthCheck;

    public static empty(): Service {
        const service = new Service();
        service.serviceUrl = new ServiceUrl();
        service.healthCheck = new ServiceHealthCheck();
        service.healthCheck.fixes = [];
        return service;
    }
}
