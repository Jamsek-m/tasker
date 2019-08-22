import {Component, Input, OnInit} from "@angular/core";
import {Service, ServiceValidation} from "../../../models/service.class";

@Component({
    selector: "tasker-service-form-client-section",
    templateUrl: "./service-form-client-section.component.html",
    styleUrls: [
        "./service-form-client-section.component.scss",
        "../service-form-page.component.scss"]
})
export class ServiceFormClientSectionComponent implements OnInit {

    @Input()
    public service: Service;

    @Input()
    public validation: ServiceValidation;

    constructor() {
    }

    ngOnInit() {
    }

}
