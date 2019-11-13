import {ConfigService} from "@mjamsek/ngx-config";
import {TaskerEnvironment} from "../environments/env.model";
import {environment} from "../environments/environment";
import {ForbiddenPageConfig, KeycloakLib, KeycloakService} from "@mjamsek/ngx-keycloak-service";
import {NavigationStart, Router} from "@angular/router";

export function AppConfigFactory() {
    return async () => {
        await ConfigService.initialize<TaskerEnvironment>({path: "/config/config.json", environment});
        console.log("Configuration: ", ConfigService.getConfig());
        await KeycloakService.initialize({
            ...ConfigService.getConfig<TaskerEnvironment>().keycloak,
            allowAnonymousAccess: true,
            roleClients: ConfigService.getConfig<TaskerEnvironment>().auth.roleClient,
            forbiddenPage: {
                external: false,
                url: "/403"
            },
            minimalRequiredRole: ConfigService.getConfig<TaskerEnvironment>().auth.minimalRequiredRole
        });
    };
}

export function AppBootstrap(router: Router) {
    return async () => {
        try {
            await KeycloakService.validateMinimalRequiredRole();
        } catch (err) {
            if (err && err.code) {
                const error = err as KeycloakLib.Error;
                if (error.code === KeycloakLib.ErrorCode.LACK_MIN_ROLE) {
                    const page: ForbiddenPageConfig = error.extra as ForbiddenPageConfig;

                    router.events.subscribe(routerEvent => {
                        if (routerEvent instanceof NavigationStart) {
                            if (routerEvent.url !== page.url) {
                                router.navigate([page.url]);
                            }
                        }
                    });

                    router.navigate([page.url]);
                }
            }
        }
    };
}

export function MetaConfigFactory() {
    return ConfigService.getValue("projectMeta");
}
export function ApiUrlFactory() {
    if (ConfigService.getValue("baseUrl")) {
        return ConfigService.getValue("baseUrl") + "/" + ConfigService.getValue("apiVersion");
    }
    return "/" + ConfigService.getValue("apiVersion");
}
export function BaseUrlFactory() {
    return ConfigService.getConfig<TaskerEnvironment>().baseUrl;
}
