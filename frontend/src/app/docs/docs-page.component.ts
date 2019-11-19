import {Component, OnInit} from "@angular/core";
import {ApiDoc, apisList} from "../content/apis";

@Component({
    selector: "tasker-docs-page",
    templateUrl: "./docs-page.component.html",
    styleUrls: ["./docs-page.component.scss"]
})
export class DocsPageComponent implements OnInit {

    public apis: ApiDoc[] = apisList;

    constructor() {
    }

    ngOnInit() {
    }

}
