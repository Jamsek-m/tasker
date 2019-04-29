import {Injectable} from "@angular/core";
import {BsModalRef, BsModalService} from "ngx-bootstrap";
import {ConfirmationDialogComponent} from "../components/confirmation-dialog/confirmation-dialog.component";
import {ToastaService, ToastOptions} from "ngx-toasta";

export interface ConfirmationDialogEvents {
    onConfirmation?: (ref: BsModalRef) => void;
    onDecline?: (ref: BsModalRef) => void;
}

export interface ConfirmationDialogOptions {
    confirmIsDestructive?: boolean;
    declineIsDestructive?: boolean;
}

export type ToastDialogType = "ok" | "error" | "warning" | "wait" | "info" | "default";

export interface ToastDialogOptions {
    // number of seconds to keep toast shown. Duration of -1 will never close toast. Default 3000 (3s).
    duration: number;
}

@Injectable({
    providedIn: "root"
})
export class MessageService {

    constructor(private modalService: BsModalService, private toastService: ToastaService) {

    }

    public openConfirmationDialog(question: string, events?: ConfirmationDialogEvents, options?: ConfirmationDialogOptions): void {
        const initialState: any = {question};
        if (events.onConfirmation) {
            initialState.onConfirmation = events.onConfirmation;
        }
        if (events.onDecline) {
            initialState.onDecline = events.onDecline;
        }
        initialState.options = options;
        this.modalService.show(ConfirmationDialogComponent, {initialState});
    }

    public openToastNotification(title: string, message: string, type?: ToastDialogType, options?: ToastDialogOptions): void {
        const toastOptions: ToastOptions = {
            title,
            msg: message,
            showClose: true,
            theme: "material"
        };
        if (options && options.duration) {
            if (options.duration === -1) {
                toastOptions.timeout = undefined;
            } else {
                toastOptions.timeout = options.duration;
            }
        } else {
            toastOptions.timeout = 3000;
        }

        if (!type) {
            type = "ok";
        }

        switch (type) {
            case "default":
                this.toastService.default(toastOptions);
                break;
            case "info":
                this.toastService.info(toastOptions);
                break;
            case "ok":
                this.toastService.success(toastOptions);
                break;
            case "wait":
                this.toastService.wait(toastOptions);
                break;
            case "error":
                this.toastService.error(toastOptions);
                break;
            case "warning":
                this.toastService.warning(toastOptions);
                break;
        }
    }

}
