import {Component, OnInit} from "@angular/core";
import {Task, taskList} from "../content/tasks";
import {Plugin, pluginList} from "../content/plugins";
import {ApiDoc, apisList} from "../content/apis";

@Component({
    selector: "tasker-docs-page",
    templateUrl: "./docs-page.component.html",
    styleUrls: ["./docs-page.component.scss"]
})
export class DocsPageComponent implements OnInit {

    public tasks: Task[] = taskList;
    public plugins: Plugin[] = pluginList;
    public apis: ApiDoc[] = apisList;

    constructor() {
    }

    ngOnInit() {
    }

}
