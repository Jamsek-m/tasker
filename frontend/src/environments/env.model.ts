export interface TaskerEnvironment {
    production: boolean;
    baseUrl: string;
    apiVersion: string;
    projectMeta: TaskerProjectMeta;
    keycloak?: TaskerKeycloakConfig;
    auth: AuthConfig;
}

export interface AuthConfig {
    roleClient: string;
}

export interface TaskerKeycloakConfig {
    realm: string;
    "auth-server-url": string;
    resource: string;
}

export interface TaskerProjectMeta {
    gitRepo: string;
    releasesPage: string;
    version: string;
    bugsPage: string;
}
