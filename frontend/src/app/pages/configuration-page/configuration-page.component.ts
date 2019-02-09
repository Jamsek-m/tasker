import {Component, OnInit} from "@angular/core";
import {ConfigService} from "../../services/config.service";
import {ConfigEntry} from "../../models/config-entry.class";

@Component({
    selector: "tasker-configuration-page",
    templateUrl: "./configuration-page.component.html",
    styleUrls: ["./configuration-page.component.scss"]
})
export class ConfigurationPageComponent implements OnInit {

    public configs: ConfigLine[];
    public newConfig: ConfigEntry;

    constructor(private configService: ConfigService) {
    }

    ngOnInit() {
        this.newConfig = new ConfigEntry();
        this.configs = [];
        this.getConfiguration();
    }

    public updateConfig(line: ConfigLine) {
        this.configService.updateConfiguration(line.config).subscribe(
            () => {
                this.getConfiguration();
            },
            (err) => {
                console.error(err);
            }
        );
    }

    public reset() {
        this.getConfiguration();
    }

    public addConfiguration() {
        this.configService.addConfiguration(this.newConfig).subscribe(
            () => {
                this.newConfig = new ConfigEntry();
                this.getConfiguration();
            },
            (err) => {
                console.error(err);
            }
        );
    }

    private getConfiguration() {
        this.configService.getConfiguration().subscribe(
            (config: ConfigEntry[]) => {
                this.configs = config.map(conf => {
                    const line = new ConfigLine();
                    line.edited = false;
                    line.config = conf;
                    return line;
                });
            },
            (err) => {
                console.error(err);
            }
        );
    }

}

class ConfigLine {
    public edited: boolean;
    public config: ConfigEntry;
}
