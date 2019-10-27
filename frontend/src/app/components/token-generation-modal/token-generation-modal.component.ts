import {Component, OnDestroy, OnInit} from "@angular/core";
import {Service} from "../../models/service.class";
import {BsModalRef} from "ngx-bootstrap";
import {ServicesService} from "../../services/services.service";
import {ServiceToken} from "../../models/token.class";
import {MessageService} from "../../services/message.service";

@Component({
    selector: "tasker-token-generation-modal",
    templateUrl: "./token-generation-modal.component.html",
    styleUrls: ["./token-generation-modal.component.scss"]
})
export class TokenGenerationModalComponent implements OnInit, OnDestroy {

    public service: Service;

    public tokenGenerated = false;
    public generatedToken: string = null;

    constructor(private ref: BsModalRef,
                private messageService: MessageService,
                private servicesService: ServicesService) {
    }

    ngOnInit() {
    }

    ngOnDestroy(): void {
        this.generatedToken = null;
    }

    public generateToken() {
        this.servicesService.createServiceToken(this.service.id).subscribe(
            (token: ServiceToken) => {
                this.tokenGenerated = true;
                this.generatedToken = token.token;
            },
            (err) => {
                console.error(err);
                this.messageService.openToastNotification("Error", "Error generating token!", "error");
                this.tokenGenerated = false;
                this.generatedToken = null;
            }
        );
    }

    public close() {
        this.generatedToken = null;
        this.ref.hide();
    }

}
