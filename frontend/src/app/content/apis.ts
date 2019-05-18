
export const apisList: ApiDoc[] = [
    {
        name: "Healthcheck",
        path: "/services/{id}/health",
        method: "GET",
        description: "Performs registered healthcheck of a service.",
        responses: [
            {
                status: 200,
                description: "Service is in good health."
            },
            {
                status: 417,
                description: "Service does not have registered healthcheck."
            },
            {
                status: 500,
                description: "Healthcheck failed."
            }
        ]
    },
    {
        name: "Start service container",
        description: "Starts registered docker container, if not running.",
        path: "/{id}/container/start",
        method: "POST",
        responses: [
            {
                status: 200,
                description: "Container has started."
            },
            {
                status: 417,
                description: "Service has no registered container."
            }
        ]
    }
];

export interface ApiDoc {
    name: string;
    description?: string;
    path: string;
    method: string;
    queryParams?: {[key: string]: ApiDoc.QueryParam};
    body?: any;
    responses: ApiDoc.Response[];
}

export namespace ApiDoc {
    export interface QueryParam {
        required?: boolean;
        description: string;
        defaultValue?: string;
        type?: string;
    }
    export interface Response {
        status: number;
        body?: any;
        description?: string;
    }
}
