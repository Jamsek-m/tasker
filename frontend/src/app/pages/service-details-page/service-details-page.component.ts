import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {ServicesService} from "../../services/services.service";
import {Service} from "../../models/service.class";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
    selector: "tasker-service-details-page",
    templateUrl: "./service-details-page.component.html",
    styleUrls: ["./service-details-page.component.scss"]
})
export class ServiceDetailsPageComponent implements OnInit {

    public service: Service = Service.empty();

    constructor(private router: Router,
                private activatedRoute: ActivatedRoute,
                private servicesService: ServicesService) {
    }

    ngOnInit() {
        this.getService();
    }

    private getService() {
        const id = this.activatedRoute.snapshot.params["id"];
        this.servicesService.getService(id).subscribe(
            (service: Service) => {
                this.service = service;
            },
            (err: HttpErrorResponse) => {
                this.router.navigate(["/404"]);
            }
        );
    }

}
