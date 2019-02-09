import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActionsService} from "../../services/actions.service";
import {AvailableActions} from "../../models/available-actions.class";
import {HttpErrorResponse} from "@angular/common/http";
import {Token} from "../../models/token.class";
import {TokenService} from "../../services/token.service";
import {TokenKeyDTO} from "../../models/token-key.dto";
import {ClipboardUtil} from "../../utils/clipboard.util";
import {Router} from "@angular/router";

@Component({
    selector: "tasker-create-token-page",
    templateUrl: "./create-token-page.component.html",
    styleUrls: ["./create-token-page.component.scss"]
})
export class CreateTokenPageComponent implements OnInit, OnDestroy {

    public availableActions: AvailableActions;
    public pluginList: string[];
    public selectedActions: {[key: string]: boolean};
    public tokenName = "";
    public tokenKey = "";
    public error = "";

    constructor(private actionsService: ActionsService, private tokenService: TokenService, private router: Router) {
    }

    ngOnInit(): void {
        this.getAllActions();
    }

    ngOnDestroy(): void {
        this.tokenKey = "";
    }

    public submitToken(): void {
        this.tokenKey = "";
        this.error = "";
        const selected = Object.keys(this.selectedActions).filter(action => {
            return this.selectedActions[action];
        }).map(action => action);

        if (selected.length === 0) {
            this.error = "No actions selected!";
            return;
        }
        if (this.tokenName.trim().length < 3) {
            this.error = "Name too short! Enter at least 3 characters";
            return;
        }

        const token = new Token();
        token.name = this.tokenName.trim();
        token.allowedActions = selected;

        this.tokenService.saveToken(token).subscribe(
            (tokenKey: TokenKeyDTO) => {
                this.tokenKey = tokenKey.generatedKey;
            },
            (err) => {
                this.error = err.error.message;
            }
        );
    }

    public copyKey(): void {
        ClipboardUtil.copyToClipboard(this.tokenKey);
    }

    public goBack(): void {
        this.router.navigate(["/"]);
    }

    private getAllActions(): void {
        this.actionsService.getAvailableActions().subscribe(
            (res: AvailableActions) => {
                this.availableActions = res;
                this.pluginList = Object.keys(this.availableActions.plugins);
                this.selectedActions = this.buildCheckBoxData(this.availableActions);
            },
            (err: HttpErrorResponse) => {
                console.error(err);
            }
        );
    }

    private buildCheckBoxData(availableActions: AvailableActions): {[key: string]: boolean} {
        const data: {[key: string]: boolean} = {};
        availableActions.tasks.forEach(task => {
            data[task] = false;
        });
        Object.keys(availableActions.plugins).forEach(plugin => {
            availableActions.plugins[plugin].forEach(pluginAction => {
                data[plugin + "." + pluginAction] = false;
            });
        });
        return data;
    }

}
