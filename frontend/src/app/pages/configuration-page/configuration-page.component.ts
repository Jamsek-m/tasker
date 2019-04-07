import {Component, OnInit} from "@angular/core";
import {ConfigService} from "../../services/config.service";
import {ConfigEntry} from "../../models/config-entry.class";
import {DockerDaemonService} from "../../services/docker-daemon.service";
import {DockerDaemon} from "../../models/docker-daemon";

@Component({
    selector: "tasker-configuration-page",
    templateUrl: "./configuration-page.component.html",
    styleUrls: ["./configuration-page.component.scss"]
})
export class ConfigurationPageComponent implements OnInit {

    public configs: ConfigLine[];
    public newConfig: ConfigEntry;
    public daemons: DaemonLine[];
    public newDaemon: DockerDaemon;

    constructor(private configService: ConfigService, private dockerDaemonService: DockerDaemonService) {
    }

    ngOnInit() {
        this.newConfig = new ConfigEntry();
        this.newDaemon = new DockerDaemon();
        this.configs = [];
        this.daemons = [];
        this.getConfiguration();
        this.getDaemons();
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

    public updateDaemon(line: DaemonLine) {
        this.dockerDaemonService.updateDaemon(line.daemon).subscribe(
            () => {
                this.getDaemons();
            },
            (err) => {
                console.error(err);
            }
        );
    }

    public resetConfig() {
        this.getConfiguration();
    }

    public resetDaemons() {
        this.getDaemons();
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

    public addDaemon() {
        this.dockerDaemonService.addDaemon(this.newDaemon).subscribe(
            () => {
                this.newDaemon = new DockerDaemon();
                this.getDaemons();
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

    private getDaemons() {
        this.dockerDaemonService.getDaemons().subscribe(
            (daemons: DockerDaemon[]) => {
                this.daemons = daemons.map(daemon => {
                    const line = new DaemonLine();
                    line.edited = false;
                    line.daemon = daemon;
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

class DaemonLine {
    public edited: boolean;
    public daemon: DockerDaemon;
}
