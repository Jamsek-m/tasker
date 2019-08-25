import {TaskerEnvironment} from "./env.model";

export const environment: TaskerEnvironment = {
    production: true,
    baseUrl: "",
    apiVersion: "v1",
    keycloakJsonPath: "/assets/config/keycloak.json",
    projectMeta: {
        version: "1.0.0",
        gitRepo: "https://github.com/Jamsek-m/tasker",
        releasesPage: "https://github.com/Jamsek-m/tasker/releases",
        bugsPage: "https://github.com/Jamsek-m/tasker/issues"
    }
};
