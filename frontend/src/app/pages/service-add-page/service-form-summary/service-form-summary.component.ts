import {Component, Input, OnInit} from "@angular/core";
import {Service} from "../../../models/service.class";

@Component({
    selector: "tasker-service-form-summary",
    templateUrl: "./service-form-summary.component.html",
    styleUrls: ["./service-form-summary.component.scss"]
})
export class ServiceFormSummaryComponent implements OnInit {

    @Input()
    public service: Service;

    constructor() {
    }

    ngOnInit() {
    }

    shortenContainerId(): string {
        return this.service.deployment.containerId.substring(0, 6) + "..." +
            this.service.deployment.containerId.substring(this.service.deployment.containerId.length - 6);
    }

}
