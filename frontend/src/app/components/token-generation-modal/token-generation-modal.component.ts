import {Component, OnDestroy, OnInit} from "@angular/core";
import {Service} from "../../models/service.class";
import {BsModalRef} from "ngx-bootstrap";
import {ServicesService} from "../../services/services.service";

@Component({
    selector: "tasker-token-generation-modal",
    templateUrl: "./token-generation-modal.component.html",
    styleUrls: ["./token-generation-modal.component.scss"]
})
export class TokenGenerationModalComponent implements OnInit, OnDestroy {

    public service: Service;

    public tokenGenerated = false;
    public generatedToken: Service.Token = null;

    constructor(private ref: BsModalRef, private servicesService: ServicesService) {
    }

    ngOnInit() {
    }

    ngOnDestroy(): void {
        this.generatedToken = null;
    }

    public generateToken() {
        this.servicesService.generateServiceToken(this.service.id).subscribe(
            (token: Service.Token) => {
                this.tokenGenerated = true;
                this.generatedToken = token;
            }
        );
    }

    public close() {
        this.generatedToken = null;
        this.ref.hide();
    }

}
