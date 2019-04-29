import {Component} from "@angular/core";
import {ToastaConfig} from "ngx-toasta";

@Component({
    selector: "tasker-root",
    templateUrl: "./app.component.html",
    styleUrls: ["./app.component.scss"]
})
export class AppComponent {

    constructor(private toastConfig: ToastaConfig) {
        this.toastConfig.theme = "material";
    }
}
