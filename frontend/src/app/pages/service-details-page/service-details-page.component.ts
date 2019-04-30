import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HealthCheckResponse, ServicesService} from "../../services/services.service";
import {Service} from "../../models/service.class";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
    selector: "tasker-service-details-page",
    templateUrl: "./service-details-page.component.html",
    styleUrls: ["./service-details-page.component.scss"]
})
export class ServiceDetailsPageComponent implements OnInit {

    public service: Service = Service.empty();
    public blockActions = false;
    public status: "bad" | "healthy" | "checking" | "undef";
    public containerInfo: any = null;

    constructor(private router: Router,
                private activatedRoute: ActivatedRoute,
                private servicesService: ServicesService) {
    }

    ngOnInit() {
        this.getService();
    }

    public startService(): void {
        this.blockActions = true;
    }

    public stopService(): void {
        this.blockActions = true;
    }

    public recreateService(): void {
        this.blockActions = true;
    }

    public getServiceInfo(): void {
        this.blockActions = true;
    }

    private getService() {
        const id = this.activatedRoute.snapshot.params["id"];
        this.servicesService.getService(id).subscribe(
            (service: Service) => {
                this.service = service;
                this.checkHealth();
            },
            (err: HttpErrorResponse) => {
                this.router.navigate(["/404"]);
            }
        );
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

}
