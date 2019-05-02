import {Component, OnInit, TemplateRef, ViewChild} from "@angular/core";
import {Router} from "@angular/router";
import {TokenService} from "../../services/token.service";
import {TokenDTO} from "../../models/token.dto";
import {Token} from "../../models/token.class";
import {BsModalRef, BsModalService, ModalOptions} from "ngx-bootstrap";
import {Service} from "../../models/service.class";
import {ServicesService} from "../../services/services.service";
import {ServiceDTO} from "../../models/service.dto";
import {Observable} from "rxjs";
import {mergeMap} from "rxjs/operators";

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

    @ViewChild("expireModal")
    private expireModal: TemplateRef<any>;

    @ViewChild("detailsModal")
    private detailsModal: TemplateRef<any>;

    constructor(private router: Router, private servicesService: ServicesService, private modalService: BsModalService) {
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
            (res: ServiceDTO) => {
                this.totalServices = res.totalCount;
                this.services = res.services;
            },
            (err: any) => {
                console.error(err);
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

    private registerServiceQueryObserver(): void {
        this.serviceQueryObservable = new Observable((observer: any) => {
            observer.next(this.query);
        }).pipe(mergeMap((nameQuery: string) => {
            return this.servicesService.queryServicesByName(nameQuery);
        }));
    }

}
