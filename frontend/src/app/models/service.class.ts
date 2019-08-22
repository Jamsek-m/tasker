import {DockerEndpoint} from "./docker-endpoint.model";

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
    public id: string;
    public containerId: string;
    public containerName: string;
    public dockerEndpoint: DockerEndpoint;
}

export class Service {
    public id: string;
    public name: string;
    public version: string;
    public description: string;
    public type: Service.Type;
    public active: boolean;
    public deployment: ServiceDeployment;
    public baseUrl: string;
    public healthcheckUrl: string;
    public majorVersion: number;
    public applicationUrl: string;

    constructor() {
    }

    public static empty(): Service {
        const service = new Service();
        return service;
    }

    public static recreate(data: any): Service {
        return Object.assign(new Service(), data) as Service;
    }

}

export namespace Service {
    export type Type = "API_SERVICE" | "WEB_APP" | "CLIENT_APP";
    export const Type = {
        API_SERVICE: "API_SERVICE" as Type,
        WEB_APP: "WEB_APP" as Type,
        CLIENT_APP: "CLIENT_APP" as Type
    };

    export const Types = [
        {
            label: "API service",
            value: "API_SERVICE" as Type
        },
        {
            label: "Web application",
            value: "WEB_APP" as Type
        },
        {
            label: "Client application",
            value: "CLIENT_APP" as Type
        }
    ];
}

export class ServiceValidation {
    public name: string;
    public version: string;
    public description: string;
    public containerId: string;
    public containerName: string;
    public url: string;
    public ApiVersion: string;
    public healthUrl: string;
    public dockerEndpoint: string;
    public type: string;
    public applicationUrl: string;
    public baseUrl: string;
    public healthcheckUrl: string;
    public majorVersion: string;

    public validEntity: boolean;

    constructor() {
        this.validEntity = true;
    }
}
