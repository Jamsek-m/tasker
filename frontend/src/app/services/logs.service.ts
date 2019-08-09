import {Inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {LogEntry} from "../models/log-entry.class";
import {map} from "rxjs/operators";
import {LogsDTO} from "../models/logsDTO";
import {API_URL} from "../injectables";

@Injectable({
    providedIn: "root"
})
export class LogsService {

    constructor(
        @Inject(API_URL) private apiUrl: string,
        private http: HttpClient
    ) {

    }

    public getLogs(limit: number, offset: number, dateFilter?: string): Observable<LogsDTO> {
        let url = `${this.apiUrl}/logs?limit=${limit}&offset=${offset}&order=logDate DESC`;
        if (dateFilter) {
            url += `&filter=logDate:GTE:'${dateFilter}'`;
        }
        url = encodeURI(url);
        return this.http.get(url, {observe: "response"}).pipe(map(res => {
            const dto = new LogsDTO();
            dto.totalCount = parseInt(res.headers.get("X-Total-Count"), 10);
            dto.logs = res.body as LogEntry[];
            return dto;
        }));
    }

}
