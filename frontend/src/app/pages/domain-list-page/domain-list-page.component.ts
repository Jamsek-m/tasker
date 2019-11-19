import {Component, OnInit} from "@angular/core";
import {Domain} from "../../models/domain.model";
import {DomainService} from "../../services/domain.service";
import {EntityList} from "../../models/common/dto.model";
import {MessageService} from "../../services/message.service";

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

    constructor(
        private domainService: DomainService,
        private messageService: MessageService
    ) {
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
                this.messageService.openToastNotification("Success", "SSL updated!", "ok");
            }
        );
    }

    public addDomain(): void {
        this.domainValidation = null;
        this.domainValidation = this.validateDomain(this.newDomain);
        if (!this.domainValidation) {
            this.domainService.addDomain(this.newDomain).subscribe(
                () => {
                    this.showAddForm = false;
                    this.newDomain = new Domain();
                    this.getDomains();
                    this.messageService.openToastNotification("Success", "Domain added!", "ok");
                }
            );
        }
    }

    public removeDomain(domain: Domain): void {
        this.messageService.openConfirmationDialog("Are you sure you want to remove domain?",
            {
                onConfirmation: ref => {
                    this.domainService.deleteDomain(domain.id).subscribe(
                        () => {
                            ref.hide();
                            this.messageService.openToastNotification("Success!", "Domain was removed!", "ok");
                            this.getDomains();
                        },
                        (err) => {
                            console.error(err);
                            this.messageService.openToastNotification("Error", "Error removing domain!", "error", {duration: -1});
                        }
                    );
                }
            }, {confirmIsDestructive: true});
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
            },
            (err) => {
                console.error(err);
                this.messageService.openToastNotification("Error", "Error retrieving list of domains!", "error", {duration: -1});
            }
        );
    }

}
