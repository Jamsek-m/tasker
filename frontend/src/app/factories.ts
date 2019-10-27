import {ConfigService} from "@mjamsek/ngx-config";
import {TaskerEnvironment} from "../environments/env.model";
import {environment} from "../environments/environment";
import {AuthService} from "./services/auth.service";

export function AppConfigFactory() {
    return async () => {
        await ConfigService.initialize<TaskerEnvironment>("/config/config.json", environment);

        await AuthService.init();

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
    return ConfigService.getValue("baseUrl");
}
