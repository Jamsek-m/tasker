import {Component, OnInit} from "@angular/core";
import {DockerDaemonService} from "../../services/docker-daemon.service";
import {DockerDaemon} from "../../models/docker-daemon";
import {Service} from "../../models/service.class";
import {DockerService} from "../../services/docker.service";
import {Observable} from "rxjs";
import {mergeMap} from "rxjs/operators";

@Component({
    selector: "tasker-service-add-page",
    templateUrl: "./service-add-page.component.html",
    styleUrls: ["./service-add-page.component.scss"]
})
export class ServiceAddPageComponent implements OnInit {

    public daemons: DockerDaemon[] = [];
    public service = Service.empty();
    public isDeployed = true;
    public isDockerized = true;
    public hasHealthcheck = true;
    public dockerContainerNameObservable: Observable<any>;

    constructor(private dockerDaemonService: DockerDaemonService,
                private dockerService: DockerService) {
    }

    ngOnInit() {
        this.initDaemons();
        this.setDefaultOptions();
        this.registerContainerSearchObserver();
    }

    public containerNameChange(containerInfo: any): void {
        let name: string = containerInfo.Names[0];
        if (name.startsWith("/")) {
            name = name.substring(1);
        }
        this.service.deployment.containerName = name;
        this.service.deployment.containerId = containerInfo.Id;
    }

    public prependUrlToHealthcheck(): void {
        this.service.healthCheck.healthUrl = this.service.serviceUrl.url + (this.service.healthCheck.healthUrl || "");
    }

    private registerContainerSearchObserver(): void {
        this.dockerContainerNameObservable = new Observable((observer: any) => {
            observer.next(this.service.deployment.containerName);
        }).pipe(mergeMap((nameQuery: string) => {
            return this.dockerService.searchContainerByName(nameQuery, this.service.deployment.dockerDaemon);
        }));
    }

    private initDaemons(): void {
        this.dockerDaemonService.getDaemons().subscribe(
            (list: DockerDaemon[]) => {
                this.daemons = list;
            },
            (err) => {
                console.error(err);
            }
        );
    }

    private setDefaultOptions(): void {
        this.service.version = "1.0.0";
        this.service.serviceUrl.url = "http://";
        this.service.deployment.containerName = "";

    }


}
