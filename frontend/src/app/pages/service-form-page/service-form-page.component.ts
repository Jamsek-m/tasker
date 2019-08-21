import {AfterViewInit, Component, ElementRef, HostListener, OnInit, ViewChild} from "@angular/core";
import {DockerEndpointsService} from "../../services/docker-endpoints.service";
import {DockerEndpoint} from "../../models/docker-endpoint.model";
import {Service, ServiceValidation} from "../../models/service.class";
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

    public daemons: DockerEndpoint[] = [];
    public service = Service.empty();
    public validation = new ServiceValidation();
    public dockerContainerNameObservable: Observable<any>;
    public mode: "edit" | "add" | "new-version";
    private serviceId: number;
    public editVersionPlaceholder: string;

    @ViewChild("formSummary", {static: false})
    public formSummary: ElementRef<HTMLDivElement>;

    @ViewChild("formSummaryContainer", {static: false})
    public formSummaryContainer: ElementRef<HTMLDivElement>;

    public sticky: number;

    constructor(private dockerDaemonService: DockerEndpointsService,
                private router: Router,
                private activatedRoute: ActivatedRoute,
                private servicesService: ServicesService,
                private messageService: MessageService,
                private dockerService: DockerService) {
    }

    ngOnInit() {
        if (this.router.url.endsWith("edit")) {
            this.mode = "edit";
            this.serviceId = this.activatedRoute.snapshot.params.id;
            this.getServiceInformation();
        } else if (this.router.url.endsWith("new-version")) {
            this.mode = "new-version";
            if (NavigationUtil.routeData) {
                this.service = Service.recreate(NavigationUtil.routeData);
                this.editVersionPlaceholder = this.service.version;
                this.service.version = "";
            } else {
                this.router.navigate(["/service/add"]);
            }
        } else {
            this.mode = "add";
        }

        this.initDaemons();
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

    public prependUrlToHealthcheck(): void {
        this.service.healthCheck.healthUrl = this.service.serviceUrl.url + (this.service.healthCheck.healthUrl || "");
    }

    public save(): void {
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
                this.router.navigate(["/"]);
            }
        }, {confirmIsDestructive: true});
    }

    public compareDaemonsFn(item1: DockerEndpoint, item2: DockerEndpoint): boolean {
        if (item1 && item2) {
            return item1.id === item2.id;
        }
        return false;
    }

    private getServiceInformation(): void {
        this.servicesService.getService(this.serviceId).subscribe(
            (service: Service) => {
                this.service = Service.recreate(service);
            },
            (err: BaseError) => {
                if (err instanceof NotFoundError) {
                    this.router.navigate(["/404"]);
                } else {
                    console.error(err);
                }
            }
        );
    }

    private registerContainerSearchObserver(): void {
        this.dockerContainerNameObservable = new Observable((observer: any) => {
            observer.next(this.service.deployment.containerName);
        }).pipe(mergeMap((nameQuery: string) => {
            return this.dockerService.searchContainerByName(nameQuery, this.service.deployment.dockerDaemon);
        }));
    }

    private initDaemons(): void {
        this.dockerDaemonService.getEndpoints().subscribe(
            (list: DockerEndpoint[]) => {
                this.daemons = list;
            },
            (err) => {
                console.error(err);
            }
        );
    }

    private setDefaultOptions(): void {
        if (this.mode === "add") {
            this.service.version = "1.0.0";
            this.service.serviceUrl.url = "http://";
            this.service.deployment.containerName = "";
        }
    }

}
