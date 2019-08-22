import {Component, Input, OnInit} from "@angular/core";
import {ClipboardUtil} from "../../utils/clipboard.util";
import {IconProp} from "@fortawesome/fontawesome-svg-core";

@Component({
    selector: "tasker-clipboard",
    templateUrl: "./clipboard.component.html",
    styleUrls: ["./clipboard.component.scss"]
})
export class ClipboardComponent implements OnInit {

    @Input()
    public data: any;

    @Input()
    public icon: "clipboard" | "copy" = "clipboard";

    public iconDefinition: IconProp = null;

    public copied = false;

    constructor() {
    }

    ngOnInit() {
        if (typeof this.data === "object") {
            this.data = JSON.stringify(this.data);
        }

        if (this.icon === "copy") {
            this.iconDefinition = ["far", "copy"];
        } else {
            this.iconDefinition = ["fas", "clipboard"];
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
