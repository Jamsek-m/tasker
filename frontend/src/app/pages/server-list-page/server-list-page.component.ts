import {Component, OnInit} from "@angular/core";
import {Server} from "../../models/server.class";
import {ServerService} from "../../services/server.service";
import {MessageService} from "../../services/message.service";
import {EntityList} from "../../models/common/dto.model";
import {Domain} from "../../models/domain.model";

@Component({
    selector: "tasker-server-list-page",
    templateUrl: "./server-list-page.component.html",
    styleUrls: ["./server-list-page.component.scss"]
})
export class ServerListPageComponent implements OnInit {

    public totalCount = 0;
    public limit = 10;
    private offset = 0;
    public servers: Server[];
    public showAddForm = false;
    public newServer: Server = new Server();
    public serverValidation: {
        name: string,
        ip: string
    } = null;

    constructor(private serverService: ServerService,
                private messageService: MessageService) {
    }

    ngOnInit() {
        this.getServers();
    }

    public pageChange(newPage: number) {
        this.offset = newPage * this.limit;
        this.getServers();
    }

    public limitChange(newLimit: number) {
        this.limit = newLimit;
        this.offset = 0;
        this.getServers();
    }

    public removeServer(server: Server) {
        this.messageService.openConfirmationDialog("Are you sure you want to remove server?",
            {
                onConfirmation: ref => {
                    this.serverService.deleteServer(server.id).subscribe(
                        () => {
                            ref.hide();
                            this.messageService.openToastNotification("Success!", "Server was removed!", "ok");
                            this.getServers();
                        },
                        (err) => {
                            console.error(err);
                            this.messageService.openToastNotification("Error", "Error removing server!", "error", {duration: -1});
                        }
                    );
                }
            }, {confirmIsDestructive: true});
    }

    public addServer() {
        this.serverValidation = null;
        this.serverValidation = this.validateServer(this.newServer);
        if (!this.serverValidation) {
            this.serverService.addServer(this.newServer).subscribe(
                () => {
                    this.showAddForm = false;
                    this.newServer = new Server();
                    this.getServers();
                    this.messageService.openToastNotification("Success", "Server added!", "ok");
                },
                (err) => {
                    console.error(err);
                    this.messageService.openToastNotification("Error", "Error adding server!", "error", {duration: -1});                }
            );
        }
    }

    private validateServer(server: Server): {name: string, ip: string} {
        if (server.name) {
            server.name = server.name.trim();
        }
        if (server.ipAddress) {
            server.ipAddress = server.ipAddress.trim();
        }

        if (!server.name || server.name.length === 0) {
            return {
                ip: null,
                name: "Server name must not be empty!"
            };
        }
        if (!server.ipAddress || server.ipAddress.length === 0) {
            return {
                ip: "Server IP must not be empty!",
                name: null
            };
        } else {
            const regex = /^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
            if (!regex.test(server.ipAddress)) {
                return {
                    ip: "Not a valid IPv4 address!",
                    name: null
                };
            }
        }
        return null;
    }

    private getServers(): void {
        this.serverService.getServers(this.limit, this.offset).subscribe(
            (list: EntityList<Server>) => {
                this.totalCount = list.count;
                this.servers = list.entities;
            },
            (err) => {
                console.error(err);
                this.messageService.openToastNotification("Error", "Error retrieving list of servers!", "error", {duration: -1});
            }
        );
    }

}
