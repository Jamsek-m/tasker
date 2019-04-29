import {Component, Input, OnInit} from "@angular/core";
import {JsonEditorOptions} from "ang-jsoneditor";

@Component({
    selector: "tasker-json-view",
    templateUrl: "./json-view.component.html",
    styleUrls: ["./json-view.component.scss"]
})
export class JsonViewComponent implements OnInit {

    @Input()
    public data: any;

    public options = new JsonEditorOptions();

    constructor() {
    }

    ngOnInit() {
        this.options = {
            ...this.options,
            mode: "view",
            expandAll: true,
            search: false,
            indentation: 2,
            theme: 0
        };
    }

}
