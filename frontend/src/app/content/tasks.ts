import {ActionParameter} from "./docs-definitions";

export const taskList: Task[] = [
    {
        name: "apiCall",
        description: "performs API call",
        parameters: [
            {
                name: "url",
                description: "url to call",
                type: "string"
            },
            {
                name: "method",
                description: "HTTP method",
                type: "string"
            }
        ]
    },
    {
        name: "shellCmd",
        description: "Not implemented yet."
    }
];

export interface Task {
    name: string;
    description?: string;
    parameters?: ActionParameter[];
}

