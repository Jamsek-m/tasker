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
    minimalRequiredRole?: string;
}

export interface TaskerKeycloakConfig {
    realm: string;
    url: string;
    clientId: string;
}

export interface TaskerProjectMeta {
    gitRepo: string;
    releasesPage: string;
    version: string;
    bugsPage: string;
}
