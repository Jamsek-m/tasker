import {AfterViewInit, Component, OnInit} from "@angular/core";
import {DashboardService} from "../../services/dashboard.service";
import {Statistics} from "../../models/statistics.model";
import {DashboardCharts} from "./dashboard.charts";
import {Chart} from "chart.js";

@Component({
    selector: "tasker-dashboard-page",
    templateUrl: "./dashboard-page.component.html",
    styleUrls: ["./dashboard-page.component.scss"]
})
export class DashboardPageComponent implements OnInit, AfterViewInit {

    public stats: Statistics;
    public charts: DashboardChart[] = [
        {
            canvasId: "service-types",
            title: "Service types"
        },
        {
            canvasId: "service-deployments",
            title: "Service deployments"
        }
    ];

    constructor(private dashboardService: DashboardService) {
    }

    ngOnInit() {

    }

    ngAfterViewInit(): void {
        this.getStatistics();
    }

    private getStatistics() {
        this.dashboardService.getStatistics().subscribe(
            (stats: Statistics) => {
                this.stats = stats;
                this.renderGraphs();
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

interface DashboardChart {
    chart?: Chart;
    canvasId: string;
    title: string;
}
