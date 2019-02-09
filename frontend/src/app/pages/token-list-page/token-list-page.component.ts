import {Component, OnInit, TemplateRef, ViewChild} from "@angular/core";
import {Router} from "@angular/router";
import {TokenService} from "../../services/token.service";
import {TokenDTO} from "../../models/token.dto";
import {Token} from "../../models/token.class";
import {BsModalRef, BsModalService, ModalOptions} from "ngx-bootstrap";

@Component({
    selector: "tasker-token-list-page",
    templateUrl: "./token-list-page.component.html",
    styleUrls: ["./token-list-page.component.scss"]
})
export class TokenListPageComponent implements OnInit {

    public limit = 10;
    private offset = 0;
    public totalTokens = 0;
    public tokens: Token[] = [];
    private expiryModalRef: BsModalRef;
    private detailsModalRef: BsModalRef;
    private toBeExpired: Token = null;
    private detailsToken: Token = null;

    @ViewChild("expireModal")
    private expireModal: TemplateRef<any>;

    @ViewChild("detailsModal")
    private detailsModal: TemplateRef<any>;

    constructor(private router: Router, private tokenService: TokenService, private modalService: BsModalService) {
    }

    ngOnInit() {
        this.getTokens();
    }

    public createNewToken(): void {
        this.router.navigate(["/new-token"]);
    }

    public showTokenDetails(token: Token): void {
        this.detailsToken = token;
        this.detailsModalRef = this.modalService.show(this.detailsModal);
    }

    public expireToken(token: Token): void {
        this.toBeExpired = token;
        this.expiryModalRef = this.modalService.show(this.expireModal);
    }

    public doTokenExpiration(answer: boolean) {
        this.expiryModalRef.hide();
        if (answer) {
            this.tokenService.expireToken(this.toBeExpired.id).subscribe(
                () => {
                    this.getTokens();
                },
                (err) => {
                    console.error(err);
                }
            );
        }
        this.toBeExpired = null;
    }

    private getTokens(): void {
        this.tokenService.getTokens(this.limit, this.offset).subscribe(
            (res: TokenDTO) => {
                this.totalTokens = res.totalCount;
                this.tokens = res.tokens;
            },
            (err) => {
                console.error(err);
            }
        );
    }

    public pageChange(newPage: number) {
        this.offset = newPage * this.limit;
        this.getTokens();
    }

    public limitChange(newLimit: number) {
        this.limit = newLimit;
        this.offset = 0;
        this.getTokens();
    }

}
