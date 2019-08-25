export interface TaskerEnvironment {
    production: boolean;
    baseUrl: string;
    apiVersion: string;
    keycloak: KeycloakConfig;
    projectMeta: TaskerProjectMeta;
}

export interface TaskerProjectMeta {
    gitRepo: string;
    releasesPage: string;
    version: string;
    bugsPage: string;
}

export interface KeycloakConfig {
    realm: string;
    authServerUrl: string;
    resource: string;
}
