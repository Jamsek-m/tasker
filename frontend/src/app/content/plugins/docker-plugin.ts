import {Plugin} from "../plugins";

export const dockerPlugin: Plugin = {
    name: "DockerPlugin",
    description: "Manages docker runtime through docker API",
    actions: [
        {
            name: "start",
            description: "Starts docker container with given id",
            parameters: [
                {
                    name: "containerId",
                    type: "string",
                    description: "id of container"
                },
                {
                    name: "configDockerAPI",
                    type: "string",
                    description: "configuration key under which docker API url is saved"
                }
            ]
        },
        {
            name: "stop",
            description: "Stops docker container with given id",
            parameters: [
                {
                    name: "containerId",
                    type: "string",
                    description: "id of container"
                },
                {
                    name: "configDockerAPI",
                    type: "string",
                    description: "configuration key under which docker API url is saved"
                }
            ]
        },
        {
            name: "getInfo",
            description: "Returns information about docker container with given id or name",
            parameters: [
                {
                    name: "containerId/containerName",
                    type: "string",
                    description: "id/name of container"
                },
                {
                    name: "configDockerAPI",
                    type: "string",
                    description: "configuration key under which docker API url is saved"
                }
            ]
        },
        {
            name: "getId",
            description: "Returns docker container id for given container name",
            parameters: [
                {
                    name: "containerName",
                    type: "string",
                    description: "name of container"
                },
                {
                    name: "configDockerAPI",
                    type: "string",
                    description: "configuration key under which docker API url is saved"
                }
            ]
        },
        {
            name: "delete",
            description: "Deletes docker container with given id. If container is running it will be stopped first",
            parameters: [
                {
                    name: "containerId",
                    type: "string",
                    description: "id of container"
                },
                {
                    name: "configDockerAPI",
                    type: "string",
                    description: "configuration key under which docker API url is saved"
                }
            ]
        },
        {
            name: "create",
            description: "Creates docker container with given name and options",
            parameters: [
                {
                    name: "containerName",
                    type: "string",
                    description: "name of container"
                },
                {
                    name: "configDockerAPI",
                    type: "string",
                    description: "configuration key under which docker API url is saved"
                },
                {
                    name: "containerData",
                    type: "CreateContainerRequest.java",
                    description: "options to be used to create docker container"
                }
            ]
        },
        {
            name: "recreate",
            description: "Recreates container with given name using same options and latest image.",
            parameters: [
                {
                    name: "containerName",
                    type: "string",
                    description: "name of container"
                },
                {
                    name: "configDockerAPI",
                    type: "string",
                    description: "configuration key under which docker API url is saved"
                }
            ]
        }
    ]
};
