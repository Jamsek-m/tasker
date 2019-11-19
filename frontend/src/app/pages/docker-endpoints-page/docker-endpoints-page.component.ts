import {Component, OnInit} from "@angular/core";
import {DockerEndpointsService} from "../../services/docker-endpoints.service";
import {DockerEndpoint} from "../../models/docker-endpoint.model";
import {MessageService} from "../../services/message.service";

@Component({
    selector: "tasker-configuration-page",
    templateUrl: "./docker-endpoints-page.component.html",
    styleUrls: ["./docker-endpoints-page.component.scss"]
})
export class DockerEndpointsPageComponent implements OnInit {
    public endpoints: SettingsLine<DockerEndpoint>[];
    public newEndpoint: DockerEndpoint;

    constructor(private messageService: MessageService,
                private dockerEndpointsService: DockerEndpointsService) {
    }

    ngOnInit() {
        this.newEndpoint = new DockerEndpoint();
        this.endpoints = [];
        this.getEndpoints();
    }

    public updateEndpoint(line: SettingsLine<DockerEndpoint>) {
        this.dockerEndpointsService.updateEndpoint(line.entity).subscribe(
            () => {
                this.getEndpoints();
                this.messageService.openToastNotification("Success", "Endpoint updated!", "ok");
            },
            (err) => {
                console.error(err);
                this.messageService.openToastNotification("Error", "Error updating endpoint!", "error", {duration: -1});
            }
        );
    }

    public resetEndpoints() {
        this.getEndpoints();
    }

    public addEndpoint() {
        this.dockerEndpointsService.addEndpoint(this.newEndpoint).subscribe(
            () => {
                this.newEndpoint = new DockerEndpoint();
                this.getEndpoints();
                this.messageService.openToastNotification("Success", "Endpoint added!", "ok");
            },
            (err) => {
                console.error(err);
                this.messageService.openToastNotification("Error", "Error adding endpoint!", "error", {duration: -1});
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
                this.getEndpoints();
                this.messageService.openToastNotification("Success!", "Docker endpoint was removed", "ok");
            },
            (err) => {
                console.error(err);
                this.messageService.openToastNotification("Error!", "Error removing docker endpoint", "error", {duration: -1});
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
                this.messageService.openToastNotification("Error", "Error retrieving list of endpoints!", "error", {duration: -1});
            }
        );
    }

}

interface SettingsLine<T> {
    edited: boolean;
    entity: T;
}
