import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {Task, taskList} from "../../content/tasks";

@Component({
    selector: "tasker-docs-task-page",
    templateUrl: "./docs-task-page.component.html",
    styleUrls: ["./docs-task-page.component.scss"]
})
export class DocsTaskPageComponent implements OnInit {

    public task: Task = null;

    constructor(private route: ActivatedRoute, private router: Router) {
    }

    ngOnInit() {
        const taskName = this.route.snapshot.params["name"];
        this.task = taskList.find(task => task.name === taskName);
        if (!this.task) {
            this.router.navigate(["/404"]);
        }
    }

}
