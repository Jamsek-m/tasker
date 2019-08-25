import {Component, OnInit} from "@angular/core";
import {ConfigService} from "../../services/config.service";
import {ConfigEntry} from "../../models/config-entry.model";
import {DockerEndpointsService} from "../../services/docker-endpoints.service";
import {DockerEndpoint} from "../../models/docker-endpoint.model";
import {MessageService} from "../../services/message.service";

@Component({
    selector: "tasker-configuration-page",
    templateUrl: "./docker-endpoints-page.component.html",
    styleUrls: ["./docker-endpoints-page.component.scss"]
})
export class DockerEndpointsPageComponent implements OnInit {

    public configs: SettingsLine<ConfigEntry>[];
    public newConfig: ConfigEntry;
    public endpoints: SettingsLine<DockerEndpoint>[];
    public newEndpoint: DockerEndpoint;

    constructor(private configService: ConfigService,
                private messageService: MessageService,
                private dockerEndpointsService: DockerEndpointsService) {
    }

    ngOnInit() {
        this.newConfig = new ConfigEntry();
        this.newEndpoint = new DockerEndpoint();
        this.configs = [];
        this.endpoints = [];
        // this.getConfiguration();
        this.getEndpoints();
    }

    public updateConfig(line: SettingsLine<ConfigEntry>) {
        this.configService.updateConfiguration(line.entity).subscribe(
            () => {
                this.getConfiguration();
            },
            (err) => {
                console.error(err);
            }
        );
    }

    public removeConfig(line: SettingsLine<ConfigEntry>) {
        this.messageService.openConfirmationDialog("Are you sure you want to remove config?", {
            onConfirmation: (ref) => {
                ref.hide();
                this.configService.deleteConfiguration(line.entity.id).subscribe(
                    () => {
                        this.getConfiguration();
                    },
                    (err) => {
                        console.error(err);
                    }
                );
            }
        }, {confirmIsDestructive: true});
    }

    public updateEndpoint(line: SettingsLine<DockerEndpoint>) {
        this.dockerEndpointsService.updateEndpoint(line.entity).subscribe(
            () => {
                this.getEndpoints();
            },
            (err) => {
                console.error(err);
            }
        );
    }

    public resetConfig() {
        this.getConfiguration();
    }

    public resetEndpoints() {
        this.getEndpoints();
    }

    public addConfiguration() {
        this.configService.addConfiguration(this.newConfig).subscribe(
            () => {
                this.newConfig = new ConfigEntry();
                this.getConfiguration();
            },
            (err) => {
                console.error(err);
            }
        );
    }

    public addEndpoint() {
        this.dockerEndpointsService.addEndpoint(this.newEndpoint).subscribe(
            () => {
                this.newEndpoint = new DockerEndpoint();
                this.getEndpoints();
            },
            (err) => {
                console.error(err);
            }
        );
    }

    public openDeleteDialog(endpoint: DockerEndpoint): void {
        this.messageService.openConfirmationDialog("Are you sure you want to delete docker endpoint?", {
            onConfirmation: (ref) => {
                ref.hide();
                this.deleteEndpoint(endpoint);
            }
        }, {confirmIsDestructive: true});
    }

    private deleteEndpoint(endpoint: DockerEndpoint): void {
        this.dockerEndpointsService.removeEndpoint(endpoint.id).subscribe(
            () => {
                this.messageService.openToastNotification("Success!", "Docker endpoint was removed", "ok");
                this.getEndpoints();
            },
            (err) => {
                console.error(err);
                this.messageService.openToastNotification("Error!", "Error removing docker endpoint", "error", {duration: -1});
            }
        );
    }

    private getConfiguration() {
        this.configService.getConfiguration().subscribe(
            (config: ConfigEntry[]) => {
                this.configs = config.map(conf => {
                    return {
                        edited: false,
                        entity: conf
                    };
                });
            },
            (err) => {
                console.error(err);
            }
        );
    }

    private getEndpoints() {
        this.dockerEndpointsService.getEndpoints().subscribe(
            (endpoints: DockerEndpoint[]) => {
                this.endpoints = endpoints.map(endpoint => {
                    return {
                        edited: false,
                        entity: endpoint
                    };
                });
            },
            (err) => {
                console.error(err);
            }
        );
    }

}

interface SettingsLine<T> {
    edited: boolean;
    entity: T;
}
