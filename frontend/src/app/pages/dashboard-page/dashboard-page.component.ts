import {AfterViewInit, Component, Inject, OnInit} from "@angular/core";
import {DashboardService} from "../../services/dashboard.service";
import {Statistics} from "../../models/statistics.model";
import {DashboardCharts} from "./dashboard.charts";
import {Chart} from "chart.js";
import {TASKER_META} from "../../injectables";
import {TaskerProjectMeta} from "../../../environments/env.model";
import {Router} from "@angular/router";
import {AuthRole} from "../../models/enums/auth-role.enum";
import {MessageService} from "../../services/message.service";
import {KeycloakService} from "@mjamsek/ngx-keycloak-service";

@Component({
    selector: "tasker-dashboard-page",
    templateUrl: "./dashboard-page.component.html",
    styleUrls: ["./dashboard-page.component.scss"]
})
export class DashboardPageComponent implements OnInit, AfterViewInit {

    public authenticated = false;
    public stats: Statistics;
    public adminAccess = [AuthRole.ADMIN];

    constructor(
        private keycloakService: KeycloakService,
        private router: Router,
        private dashboardService: DashboardService,
        private messageService: MessageService,
        @Inject(TASKER_META) public projectMeta: TaskerProjectMeta) {
    }

    ngOnInit() {
        this.authenticated = this.keycloakService.isAuthenticated();
    }

    ngAfterViewInit(): void {
        if (this.keycloakService.isAuthenticated()) {
            this.getStatistics();
        }
    }

    public goTo(url: string, allowForRoles?: string[]): void {
        if (this.userHasPermission(allowForRoles)) {
            this.router.navigate([url]);
        }
    }

    public userHasPermission(allowForRoles: string[]): boolean {
        if (allowForRoles) {
            const userRole = allowForRoles.filter(role => this.keycloakService.hasRole(role));
            return userRole.length > 0;
        }
        return true;
    }

    private getStatistics() {
        this.dashboardService.getStatistics().subscribe(
            (stats: Statistics) => {
                this.stats = stats;
                this.renderGraphs();
            },
            (err) => {
                console.error(err);
                this.messageService.openToastNotification("Error", "Error retrieving statistics!", "error", {duration: -1});
            }
        );
    }

    private renderGraphs() {
        this.renderServiceDistributionGraph();
        this.renderServiceDeploymentGraph();
    }

    private renderServiceDistributionGraph() {
        const canvas: HTMLCanvasElement = document.getElementById("service-types") as HTMLCanvasElement;
        const context = canvas.getContext("2d");
        DashboardCharts.drawServiceDistributionChart(context, this.stats);
    }

    private renderServiceDeploymentGraph() {
        const canvas: HTMLCanvasElement = document.getElementById("service-deployments") as HTMLCanvasElement;
        const context = canvas.getContext("2d");
        DashboardCharts.drawServiceDeploymentChart(context, this.stats);
    }

}
