import {AfterViewInit, Component, ElementRef, HostListener, OnInit, ViewChild} from "@angular/core";
import {DockerEndpointsService} from "../../services/docker-endpoints.service";
import {DockerEndpoint} from "../../models/docker-endpoint.model";
import {Service, ServiceDeployment, ServiceValidation} from "../../models/service.class";
import {DockerService} from "../../services/docker.service";
import {Observable} from "rxjs";
import {mergeMap} from "rxjs/operators";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "../../services/message.service";
import {BsModalRef} from "ngx-bootstrap";
import {ServicesService} from "../../services/services.service";
import {BaseError} from "../../errors/base.error";
import {NotFoundError} from "../../errors/not-found.error";
import {NavigationUtil} from "../../utils/navigation.util";

@Component({
    selector: "tasker-service-add-page",
    templateUrl: "./service-form-page.component.html",
    styleUrls: ["./service-form-page.component.scss"]
})
export class ServiceFormPageComponent implements OnInit, AfterViewInit {

    public service = new Service();
    public hasDeployment = false;
    public validation = new ServiceValidation();

    public mode: "edit" | "add" | "new-version";
    public editVersionPlaceholder: string;

    public dockerEndpoints: DockerEndpoint[] = [];
    public typeEnum = Service.Types;
    public dockerContainerNameObservable: Observable<any>;

    @ViewChild("formSummary", {static: false})
    public formSummary: ElementRef<HTMLDivElement>;

    @ViewChild("formSummaryContainer", {static: false})
    public formSummaryContainer: ElementRef<HTMLDivElement>;

    public sticky: number;

    constructor(private dockerEndpointsService: DockerEndpointsService,
                private router: Router,
                private activatedRoute: ActivatedRoute,
                private servicesService: ServicesService,
                private messageService: MessageService,
                private dockerService: DockerService) {
    }

    ngOnInit() {
        if (this.router.url.endsWith("edit")) {
            this.mode = "edit";
            this.getServiceInformation();
        } else if (this.router.url.endsWith("new-version")) {
            this.mode = "new-version";
            if (NavigationUtil.routeData) {
                this.service = Service.recreate(NavigationUtil.routeData);
                this.editVersionPlaceholder = this.service.version;
                this.service.version = "";
                this.hasDeployment = !!this.service.deployment;
            } else {
                this.router.navigate(["/service/add"]);
            }
        } else {
            this.mode = "add";
        }

        this.getDockerEndpoints();
        this.setDefaultOptions();
        this.registerContainerSearchObserver();
    }

    ngAfterViewInit(): void {
        this.sticky = this.formSummary.nativeElement.offsetTop;
        this.formSummary.nativeElement.style.width = `${this.formSummaryContainer.nativeElement.offsetWidth}px`;
    }

    @HostListener("window:scroll")
    private onScroll(): void {
        if (window.pageYOffset >= this.sticky) {
            this.formSummary.nativeElement.classList.add("sticky");
        } else {
            this.formSummary.nativeElement.classList.remove("sticky");
        }
    }

    public containerNameChange(containerInfo: any): void {
        let name: string = containerInfo.Names[0];
        if (name.startsWith("/")) {
            name = name.substring(1);
        }
        this.service.deployment.containerName = name;
        this.service.deployment.containerId = containerInfo.Id;
    }

    public save(): void {

        if (!this.hasDeployment) {
            this.service.deployment = null;
        }

        this.validation = this.servicesService.validateService(this.service);
        if (this.validation.validEntity) {

            if (this.mode === "add" || this.mode === "new-version") {
                this.servicesService.createService(this.service).subscribe(
                    (created: Service) => {
                        this.messageService.openToastNotification("Success!", "Service created!", "ok");
                        this.router.navigate(["/service", created.id]);
                    },
                    (err: BaseError) => {
                        console.error(err);
                        this.messageService.openToastNotification("Error!", err.message, "error");
                    }
                );
            } else {
                this.servicesService.updateService(this.service).subscribe(
                    (updated: Service) => {
                        this.messageService.openToastNotification("Success!", "Service created!", "ok");
                        this.router.navigate(["/service", updated.id]);
                    },
                    (err: BaseError) => {
                        console.error(err);
                        this.messageService.openToastNotification("Error!", err.message, "error");
                    }
                );
            }
        }
    }

    public cancel(): void {
        this.messageService.openConfirmationDialog("Are you sure? All unsaved data will be lost.", {
            onConfirmation: (ref: BsModalRef) => {
                ref.hide();
                this.router.navigate(["/services"]);
            }
        }, {confirmIsDestructive: true});
    }

    public compareDaemonsFn(item1: DockerEndpoint, item2: DockerEndpoint): boolean {
        if (item1 && item2) {
            return item1.id === item2.id;
        }
        return false;
    }

    public initDeployment() {
        if (!this.service.deployment) {
            this.service.deployment = new ServiceDeployment();
        }
    }

    private getServiceInformation(): void {
        const serviceId = this.activatedRoute.snapshot.params.id;
        this.servicesService.getService(serviceId).subscribe(
            (service: Service) => {
                this.service = service;
                this.hasDeployment = !!this.service.deployment;
            },
            (err: BaseError) => {
                if (err instanceof NotFoundError) {
                    this.router.navigate(["/404"]);
                } else {
                    console.error(err);
                    this.messageService.openToastNotification("Error", "Error retrieving details of service!", "error", {duration: -1});
                }
            }
        );
    }

    private registerContainerSearchObserver(): void {
        this.dockerContainerNameObservable = new Observable((observer: any) => {
            observer.next(this.service.deployment.containerName);
        }).pipe(mergeMap((nameQuery: string) => {
            return this.dockerService.searchContainerByName(nameQuery, this.service.deployment.dockerEndpoint);
        }));
    }

    private getDockerEndpoints(): void {
        this.dockerEndpointsService.getEndpoints().subscribe(
            (list: DockerEndpoint[]) => {
                this.dockerEndpoints = list;
            },
            (err) => {
                console.error(err);
                this.messageService.openToastNotification("Error", "Error retrieving list of endpoints!", "error", {duration: -1});
            }
        );
    }

    private setDefaultOptions(): void {
        if (this.mode === "add") {
            this.service.version = "1.0.0";
        }
    }

}
