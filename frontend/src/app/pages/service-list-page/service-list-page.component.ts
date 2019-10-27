import {Component, OnInit, TemplateRef, ViewChild} from "@angular/core";
import {Router} from "@angular/router";
import {BsModalService} from "ngx-bootstrap";
import {Service} from "../../models/service.class";
import {ServicesService} from "../../services/services.service";
import {Observable} from "rxjs";
import {mergeMap} from "rxjs/operators";
import {EntityList} from "../../models/common/dto.model";
import {MessageService} from "../../services/message.service";

@Component({
    selector: "tasker-token-list-page",
    templateUrl: "./service-list-page.component.html",
    styleUrls: ["./service-list-page.component.scss"]
})
export class ServiceListPageComponent implements OnInit {

    public limit = 10;
    private offset = 0;
    public totalServices = 0;
    public services: Service[] = [];
    public query = "";
    public serviceQueryObservable: Observable<Service[]>;

    @ViewChild("expireModal", {static: false})
    private expireModal: TemplateRef<any>;

    @ViewChild("detailsModal", {static: false})
    private detailsModal: TemplateRef<any>;

    constructor(private router: Router,
                private servicesService: ServicesService,
                private messageService: MessageService) {
    }

    ngOnInit() {
        this.getServices();
        this.registerServiceQueryObserver();
    }

    public createNewService(): void {
        this.router.navigate(["/service/add"]);
    }

    private getServices(): void {
        this.servicesService.getServices(this.limit, this.offset).subscribe(
            (res: EntityList<Service>) => {
                this.totalServices = res.count;
                this.services = res.entities;
            },
            (err: any) => {
                console.error(err);
                this.messageService.openToastNotification("Error", "Error retrieving list of services!", "error", {duration: -1});
            }
        );
    }

    public pageChange(newPage: number) {
        this.offset = newPage * this.limit;
        this.getServices();
    }

    public limitChange(newLimit: number) {
        this.limit = newLimit;
        this.offset = 0;
        this.getServices();
    }

    public selectSearchResult(service: Service) {
        this.query = "";
        this.router.navigate(["/service", service.id]);
    }

    public displayType(service: Service): string {
        switch (service.type) {
            case "API_SERVICE":
                return "API";
            case "CLIENT_APP":
                return "CLIENT";
            case "WEB_APP":
                return "WEB";
            default:
                return "";
        }
    }

    private registerServiceQueryObserver(): void {
        this.serviceQueryObservable = new Observable((observer: any) => {
            observer.next(this.query);
        }).pipe(mergeMap((nameQuery: string) => {
            return this.servicesService.queryServicesByName(nameQuery);
        }));
    }

}
