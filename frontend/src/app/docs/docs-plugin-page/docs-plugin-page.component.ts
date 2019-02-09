import {Component, OnInit} from "@angular/core";
import {Plugin, pluginList} from "../../content/plugins";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: "tasker-docs-plugin-page",
    templateUrl: "./docs-plugin-page.component.html",
    styleUrls: ["./docs-plugin-page.component.scss"]
})
export class DocsPluginPageComponent implements OnInit {

    public plugin: Plugin = null;

    constructor(private route: ActivatedRoute, private router: Router) {
    }

    ngOnInit() {
        const pluginName = this.route.snapshot.params["name"];
        this.plugin = pluginList.find(plugin => plugin.name === pluginName);
        if (!this.plugin) {
            this.router.navigate(["/404"]);
        }
    }

}
