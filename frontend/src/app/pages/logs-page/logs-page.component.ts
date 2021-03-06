import {Component, OnInit} from "@angular/core";
import {LogsService} from "../../services/logs.service";
import {LogEntry} from "../../models/log-entry.model";
import {HttpErrorResponse} from "@angular/common/http";
import {EntityList} from "../../models/common/dto.model";
import {MessageService} from "../../services/message.service";

@Component({
    selector: "tasker-logs-page",
    templateUrl: "./logs-page.component.html",
    styleUrls: ["./logs-page.component.scss"]
})
export class LogsPageComponent implements OnInit {

    public totalLogs = 0;
    public limit = 10;
    private offset = 0;
    public logs: LogEntry[];

    public dateFilter: Date;
    public timeFilter: Date;

    constructor(private logsService: LogsService,
                private messageService: MessageService) {
    }

    ngOnInit() {
        this.logs = [];
        this.dateFilter = new Date();
        this.timeFilter = new Date();
        this.getLogs();
    }

    public getLogs(dateFilter?: string): void {
        this.logsService.getLogs(this.limit, this.offset, dateFilter).subscribe(
            (list: EntityList<LogEntry>) => {
                this.logs = list.entities;
                this.totalLogs = list.count;
            },
            (err: HttpErrorResponse) => {
                console.error(err);
                this.messageService.openToastNotification("Error", "Error retrieving logs!", "error", {duration: -1});
            }
        );
    }

    public filterLogs() {
        const filteredDate = new Date();
        filteredDate.setDate(this.dateFilter.getDate());
        filteredDate.setMonth(this.dateFilter.getMonth());
        filteredDate.setFullYear(this.dateFilter.getFullYear());
        filteredDate.setHours(this.timeFilter.getHours());
        filteredDate.setMinutes(this.timeFilter.getMinutes());
        filteredDate.setSeconds(this.timeFilter.getSeconds());
        filteredDate.setMilliseconds(this.timeFilter.getMilliseconds());
        this.offset = 0;
        this.getLogs(filteredDate.toISOString());
    }

    public pageChange(newPage: number) {
        this.offset = newPage * this.limit;
        this.getLogs();
    }

    public limitChange(newLimit: number) {
        this.limit = newLimit;
        this.offset = 0;
        this.getLogs();
    }

}
