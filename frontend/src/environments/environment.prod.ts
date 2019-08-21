import {TaskerEnvironment} from "./env.model";

export const environment: TaskerEnvironment = {
    production: true,
    baseUrl: "",
    apiVersion: "v1",
    keycloak: {
        realm: "mjamsek-test",
        authServerUrl: "https://keycloak.mjamsek.com/auth",
        resource: "tasker-public"
    }
};
