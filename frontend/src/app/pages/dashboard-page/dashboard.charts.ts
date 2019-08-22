import {Chart} from "chart.js";
import {Statistics} from "../../models/statistics.model";

export class DashboardCharts {

    public static drawServiceDistributionChart(context: CanvasRenderingContext2D, stats: Statistics): Chart {
        const labels = ["API", "WEB", "CLIENT"];
        const data = [stats.totalApiServices, stats.totalWebApps, stats.totalClientApps];

        return new Chart(context, {
            type: "doughnut",
            data: {
                labels,
                datasets: [
                    {
                        data,
                        backgroundColor: [
                            "rgba(255, 99, 132, 0.2)",
                            "rgba(54, 162, 235, 0.2)",
                            "rgba(255, 206, 86, 0.2)",
                            "rgba(75, 192, 192, 0.2)",
                            "rgba(153, 102, 255, 0.2)",
                            "rgba(255, 159, 64, 0.2)"
                        ],
                        borderColor: [
                            "rgba(255, 99, 132, 1)",
                            "rgba(54, 162, 235, 1)",
                            "rgba(255, 206, 86, 1)",
                            "rgba(75, 192, 192, 1)",
                            "rgba(153, 102, 255, 1)",
                            "rgba(255, 159, 64, 1)"
                        ],
                        borderWidth: 1
                    }
                ]
            },
            options: {
                legend: {
                    position: "top"
                },
                tooltips: {
                    enabled: true,
                    mode: "single"
                }
            }
        });
    }

    public static drawServiceDeploymentChart(context: CanvasRenderingContext2D, stats: Statistics): Chart {
        const labels = ["Deployed", "Not deployed"];
        const data = [stats.totalDeployedServices, stats.totalServices - stats.totalDeployedServices];

        return new Chart(context, {
            type: "doughnut",
            data: {
                labels,
                datasets: [
                    {
                        data,
                        backgroundColor: [
                            "rgba(255, 99, 132, 0.2)",
                            "rgba(54, 162, 235, 0.2)",
                            "rgba(255, 206, 86, 0.2)",
                            "rgba(75, 192, 192, 0.2)",
                            "rgba(153, 102, 255, 0.2)",
                            "rgba(255, 159, 64, 0.2)"
                        ],
                        borderColor: [
                            "rgba(255, 99, 132, 1)",
                            "rgba(54, 162, 235, 1)",
                            "rgba(255, 206, 86, 1)",
                            "rgba(75, 192, 192, 1)",
                            "rgba(153, 102, 255, 1)",
                            "rgba(255, 159, 64, 1)"
                        ],
                        borderWidth: 1
                    }
                ]
            },
            options: {
                legend: {
                    position: "top"
                },
                tooltips: {
                    enabled: true,
                    mode: "single"
                }
            }
        });
    }

}
