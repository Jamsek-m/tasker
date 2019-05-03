import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HealthCheckResponse, ServicesService} from "../../services/services.service";
import {Service} from "../../models/service.class";
import {HttpErrorResponse} from "@angular/common/http";
import {DockerService} from "../../services/docker.service";
import {MessageService} from "../../services/message.service";
import {DockerState} from "../../models/docker-state.class";
import {BsModalRef} from "ngx-bootstrap";

@Component({
    selector: "tasker-service-details-page",
    templateUrl: "./service-details-page.component.html",
    styleUrls: ["./service-details-page.component.scss"]
})
export class ServiceDetailsPageComponent implements OnInit {

    public service: Service = Service.empty();
    public containerState: DockerState = new DockerState();
    public blockActions = false;
    public status: "bad" | "healthy" | "checking" | "undef";
    public containerInfo: any = null;

    constructor(private router: Router,
                private activatedRoute: ActivatedRoute,
                private dockerService: DockerService,
                private messageService: MessageService,
                private servicesService: ServicesService) {
    }

    ngOnInit() {
        this.getService();
    }

    public startService(): void {
        this.blockActions = true;
        this.dockerService.startContainer(this.service).subscribe(
            () => {
                this.blockActions = false;
                this.messageService.openToastNotification("Success!", "Container was started!");
                this.getContainerState();
            },
            (err) => {
                this.blockActions = false;
                console.error(err);
                this.messageService.openToastNotification("Error!", "Error starting container!", "error");
            }
        );
    }

    public stopService(): void {
        this.messageService.openConfirmationDialog("Are you sure you want to stop container?", {
            onConfirmation: (ref: BsModalRef) => {
                ref.hide();
                this.blockActions = true;
                this.dockerService.stopContainer(this.service).subscribe(
                    () => {
                        this.blockActions = false;
                        this.messageService.openToastNotification("Success!", "Container was stopped!");
                        this.getContainerState();
                    },
                    (err) => {
                        this.blockActions = false;
                        console.error(err);
                        this.messageService.openToastNotification("Error!", "Error stopping container!", "error");
                    }
                );
            }
        }, {confirmIsDestructive: true});
    }

    public recreateService(): void {
        this.messageService.openConfirmationDialog("Are you sure you want to recreate container?", {
            onConfirmation: (ref: BsModalRef) => {
                ref.hide();
                this.blockActions = true;
                this.dockerService.recreateContainer(this.service).subscribe(
                    () => {
                        this.blockActions = false;
                        this.messageService.openToastNotification("Success!", "Container was recreated!");
                        this.getService();
                        this.getContainerState();
                    },
                    (err) => {
                        this.blockActions = false;
                        console.error(err);
                        this.messageService.openToastNotification("Error!", "Error recreating container!", "error");
                    }
                );
            }
        });
    }

    public back(): void {
        this.router.navigate(["/"]);
    }

    public checkHealth(): void {
        if (this.status === "undef") {
            return;
        }
        this.status = "checking";
        setTimeout(() => {
            this.servicesService.doHealthCheck(this.service.id).subscribe(
                (response: HealthCheckResponse) => {
                    if (response === HealthCheckResponse.OK) {
                        this.status = "healthy";
                    } else if (response === HealthCheckResponse.ERROR) {
                        this.status = "bad";
                    } else {
                        this.status = "undef";
                    }
                }
            );
        }, 250);
    }

    public getContainerInfo(): void {
        this.blockActions = true;
        this.dockerService.getContainerInfo(this.service).subscribe(
            (info) => {
                this.containerInfo = info;
                this.blockActions = false;
            },
            (err) => {
                this.blockActions = false;
                console.error(err);
                this.messageService.openToastNotification("Error", "Error fetching container info!", "error");
            }
        );
    }

    public goToDaemons(): void {
        this.router.navigate(["/config"]);
    }

    private getService() {
        const id = this.activatedRoute.snapshot.params["id"];
        this.servicesService.getService(id).subscribe(
            (service: Service) => {
                this.service = service;
                this.checkHealth();
                this.getContainerState();
            },
            (err: HttpErrorResponse) => {
                this.router.navigate(["/404"]);
            }
        );
    }

    private getContainerState(): void {
        this.dockerService.getContainerState(this.service).subscribe(
            (state: DockerState) => {
                this.containerState = state;
            },
            (err) => {
                console.error(err);
            }
        );
    }
}
