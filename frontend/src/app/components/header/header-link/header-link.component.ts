import {Component, Input, OnInit} from "@angular/core";
import {MenuItem} from "../../../content/menu-items";

@Component({
    selector: "tasker-header-link",
    templateUrl: "./header-link.component.html",
    styleUrls: ["./header-link.component.scss"]
})
export class HeaderLinkComponent implements OnInit {

    @Input()
    public item: MenuItem;

    constructor() {
    }

    ngOnInit() {
    }

}
