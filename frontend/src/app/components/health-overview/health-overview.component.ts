import {Component, Input, OnInit} from "@angular/core";
import {ServicesService} from "../../services/services.service";
import {Service} from "../../models/service.class";
import {HealthCheckResponse} from "../../models/enums/healthcheck-response.enum";

@Component({
    selector: "tasker-health-overview",
    templateUrl: "./health-overview.component.html",
    styleUrls: ["./health-overview.component.scss"]
})
export class HealthOverviewComponent implements OnInit {

    @Input()
    public service: Service;

    public state: "bad" | "healthy" | "checking" | "undef";

    constructor(private servicesService: ServicesService) {
    }

    ngOnInit() {
        this.checkHealth();
    }

    public checkHealth(): void {
        if (this.state === "undef") {
            return;
        }
        this.state = "checking";
        setTimeout(() => {
            this.servicesService.doHealthCheck(this.service.id).subscribe(
                (response: HealthCheckResponse) => {
                    if (response === HealthCheckResponse.OK) {
                        this.state = "healthy";
                    } else if (response === HealthCheckResponse.ERROR) {
                        this.state = "bad";
                    } else {
                        this.state = "undef";
                    }
                }
            );
        }, 500);
    }

}
