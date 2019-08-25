export interface TaskerEnvironment {
    production: boolean;
    baseUrl: string;
    apiVersion: string;
    keycloakJsonPath: string;
    projectMeta: TaskerProjectMeta;
}

export interface TaskerProjectMeta {
    gitRepo: string;
    releasesPage: string;
    version: string;
    bugsPage: string;
}
