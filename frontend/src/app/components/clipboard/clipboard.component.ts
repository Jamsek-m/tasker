import {Component, Input, OnInit} from "@angular/core";
import {ClipboardUtil} from "../../utils/clipboard.util";

@Component({
    selector: "tasker-clipboard",
    templateUrl: "./clipboard.component.html",
    styleUrls: ["./clipboard.component.scss"]
})
export class ClipboardComponent implements OnInit {

    @Input()
    public data: any;

    public copied = false;

    constructor() {
    }

    ngOnInit() {
        if (typeof this.data === "object") {
            this.data = JSON.stringify(this.data);
        }
    }

    public copy(): void {
        ClipboardUtil.copyToClipboard(this.data);
        this.copied = true;
        setTimeout(() => {
            this.copied = false;
        }, 250);
    }

}
