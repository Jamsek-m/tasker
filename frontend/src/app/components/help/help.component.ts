import {Component, Input, OnInit} from "@angular/core";

@Component({
    selector: "tasker-help",
    templateUrl: "./help.component.html",
    styleUrls: ["./help.component.scss"]
})
export class HelpComponent implements OnInit {

    @Input()
    public help: string;

    constructor() {
    }

    ngOnInit() {
    }

}
