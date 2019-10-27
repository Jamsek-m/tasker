// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import {TaskerEnvironment} from "./env.model";

export const environment: TaskerEnvironment = {
    production: false,
    baseUrl: "http://localhost:8080",
    apiVersion: "v1",
    projectMeta: {
        version: "1.0.0-SNAPSHOT",
        gitRepo: "https://github.com/Jamsek-m/tasker",
        releasesPage: "https://github.com/Jamsek-m/tasker/releases",
        bugsPage: "https://github.com/Jamsek-m/tasker/issues"
    },
    keycloak: {
        realm: "mjamsek-test",
        "auth-server-url": "https://keycloak.mjamsek.com/auth",
        resource: "tasker-service-public"
    }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
