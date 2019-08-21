export interface TaskerEnvironment {
    production: boolean;
    baseUrl: string;
    apiVersion: string;
    keycloak: KeycloakConfig;
}

export interface KeycloakConfig {
    realm: string;
    authServerUrl: string;
    resource: string;
}
