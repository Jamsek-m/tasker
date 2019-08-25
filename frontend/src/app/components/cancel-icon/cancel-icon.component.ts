import {Component, EventEmitter, OnInit, Output} from "@angular/core";

@Component({
    selector: "tasker-cancel-icon",
    templateUrl: "./cancel-icon.component.html",
    styleUrls: ["./cancel-icon.component.scss"]
})
export class CancelIconComponent implements OnInit {

    @Output()
    public whenClicked: EventEmitter<void> = new EventEmitter<void>();

    constructor() {
    }

    ngOnInit() {
    }

}
