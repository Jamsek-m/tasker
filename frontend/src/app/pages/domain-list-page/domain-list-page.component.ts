import {Component, OnInit} from "@angular/core";
import {Domain} from "../../models/domain.model";
import {DomainService} from "../../services/domain.service";
import {EntityList} from "../../models/common/dto.model";

@Component({
    selector: "tasker-domain-list-page",
    templateUrl: "./domain-list-page.component.html",
    styleUrls: ["./domain-list-page.component.scss"]
})
export class DomainListPageComponent implements OnInit {

    public totalCount = 0;
    public limit = 10;
    private offset = 0;
    public domains: Domain[];
    public showAddForm = false;
    public newDomain: Domain = new Domain();
    public domainValidation: string = null;

    constructor(private domainService: DomainService) {
    }

    ngOnInit() {
        this.getDomains();
    }

    public pageChange(newPage: number) {
        this.offset = newPage * this.limit;
        this.getDomains();
    }

    public limitChange(newLimit: number) {
        this.limit = newLimit;
        this.offset = 0;
        this.getDomains();
    }

    public updateSSL(domain: Domain): void {
        this.domainService.updateDomain(domain).subscribe(
            (updatedDomain: Domain) => {
                domain = updatedDomain;
            }
        );
    }

    public addDomain(): void {
        this.domainValidation = null;
        this.domainValidation = this.validateDomain(this.newDomain);
        if (!this.domainValidation) {
            this.domainService.addDomain(this.newDomain).subscribe(
                () => {
                    this.getDomains();
                }
            );
        }
    }

    private validateDomain(domain: Domain): string | null {
        if (domain.domain) {
            domain.domain = domain.domain.trim();
        }

        if (!domain.domain || domain.domain.length === 0) {
            return "Domain must not be empty!";
        }
        return null;
    }

    private getDomains(): void {
        this.domainService.getDomains(this.limit, this.offset).subscribe(
            (list: EntityList<Domain>) => {
                this.totalCount = list.count;
                this.domains = list.entities;
            }
        );
    }

}
