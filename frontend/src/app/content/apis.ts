
export const apisList: ApiDoc[] = [
    {
        name: "Start service container",
        description: "Starts registered docker container, if not running.",
        path: "/public/services/{id}/container/start",
        method: "POST",
        responses: [
            {
                status: 200,
                description: "Container has started."
            },
            {
                status: 404,
                description: "Service does not exists."
            },
            {
                status: 417,
                description: "Service has no registered container."
            }
        ],
        params: {
            "X-Tasker-Key": {
                in: "Header",
                description: "Authentication token",
                type: "string",
                required: true,
                defaultValue: ""
            }
        },
    },
    {
        name: "Recreate service container with latest image",
        description: "Removes old container, creates new one with latest image and starts it",
        path: "/public/services/{id}/container/recreate",
        method: "POST",
        params: {
            "X-Tasker-Key": {
                in: "Header",
                description: "Authentication token",
                type: "string",
                required: true,
                defaultValue: ""
            }
        },
        responses: [
            {
                status: 200,
                description: "Container has been recreated."
            },
            {
                status: 404,
                description: "Service does not exists."
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
    params?: {[key: string]: ApiDoc.QueryParam};
    body?: any;
    responses: ApiDoc.Response[];
}

export namespace ApiDoc {
    export interface QueryParam {
        required?: boolean;
        description: string;
        defaultValue?: string;
        type?: string;
        in: "Header" | "Query" | "Body" | "Cookie";
    }
    export interface Response {
        status: number;
        body?: any;
        description?: string;
    }
}
