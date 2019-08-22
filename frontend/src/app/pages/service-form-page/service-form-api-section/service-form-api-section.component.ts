import {Component, Input, OnInit} from "@angular/core";
import {Service, ServiceValidation} from "../../../models/service.class";

@Component({
    selector: "tasker-service-form-api-section",
    templateUrl: "./service-form-api-section.component.html",
    styleUrls: [
        "./service-form-api-section.component.scss",
        "../service-form-page.component.scss"
    ]
})
export class ServiceFormApiSectionComponent implements OnInit {

    @Input()
    public service: Service;

    @Input()
    public validation: ServiceValidation;

    constructor() {
    }

    ngOnInit() {
    }

}
