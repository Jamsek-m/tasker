import {Component, Input, OnInit} from "@angular/core";
import {ApiDoc} from "../../content/apis";

@Component({
    selector: "tasker-docs-api-entry",
    templateUrl: "./docs-api-entry.component.html",
    styleUrls: ["./docs-api-entry.component.scss"]
})
export class DocsApiEntryComponent implements OnInit {

    @Input()
    public api: ApiDoc;

    constructor() {
    }

    ngOnInit() {
    }

}
