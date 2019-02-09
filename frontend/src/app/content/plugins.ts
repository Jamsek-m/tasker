import {ActionParameter} from "./docs-definitions";
import {dockerPlugin} from "./plugins/docker-plugin";

export const pluginList: Plugin[] = [
    dockerPlugin
];

export interface Plugin {
    name: string;
    description?: string;
    actions: PluginAction[];
}

export interface PluginAction {
    name: string;
    description: string;
    parameters?: ActionParameter[];
}
