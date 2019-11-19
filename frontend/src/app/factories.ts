import {ConfigService} from "@mjamsek/ngx-config";
import {TaskerEnvironment} from "../environments/env.model";
import {environment} from "../environments/environment";
import {KeycloakService} from "@mjamsek/ngx-keycloak-service";

export function AppConfigFactory() {
    return async () => {
        await ConfigService.initialize<TaskerEnvironment>({path: "/config/config.json", environment});

        await KeycloakService.initialize({
            ...ConfigService.getConfig<TaskerEnvironment>().keycloak,
            allowAnonymousAccess: true,
            roleClients: ConfigService.getConfig<TaskerEnvironment>().auth.roleClient,
            forbiddenPage: {
                external: false,
                url: "/403"
            }
        });
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
