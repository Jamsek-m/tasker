import {Component, OnInit} from "@angular/core";
import {BsModalRef} from "ngx-bootstrap";
import {ConfirmationDialogOptions} from "../../services/message.service";

@Component({
    selector: "tasker-confirmation-dialog",
    templateUrl: "./confirmation-dialog.component.html",
    styleUrls: ["./confirmation-dialog.component.scss"]
})
export class ConfirmationDialogComponent implements OnInit {

    public question: string;

    public buttonStyles = {
        confirm: "primary",
        decline: "default"
    };

    public options: ConfirmationDialogOptions;

    public onConfirmation = (ref: BsModalRef) => {
        this.modalRef.hide();
    }

    public onDecline = (ref: BsModalRef) => {
        this.modalRef.hide();
    }

    constructor(public modalRef: BsModalRef) {
    }

    ngOnInit() {
        if (this.options && this.options.confirmIsDestructive) {
            this.buttonStyles.confirm = "danger";
        }
        if (this.options && this.options.declineIsDestructive) {
            this.buttonStyles.decline = "danger";
        }
    }

    confirm() {
        this.onConfirmation(this.modalRef);
    }

    decline() {
        this.onDecline(this.modalRef);
    }

}
