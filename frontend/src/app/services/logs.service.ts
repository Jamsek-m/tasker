import {Inject, Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {LogEntry} from "../models/log-entry.model";
import {map} from "rxjs/operators";
import {API_URL} from "../injectables";
import {EntityList} from "../models/common/dto.model";

@Injectable({
    providedIn: "root"
})
export class LogsService {

    constructor(
        @Inject(API_URL) private apiUrl: string,
        private http: HttpClient
    ) {

    }

    public getLogs(limit: number, offset: number, dateFilter?: string): Observable<EntityList<LogEntry>> {
        const url = `${this.apiUrl}/logs`;

        const params = {
            limit: limit.toString(10),
            offset: offset.toString(10),
            order: "timestamp DESC"
        };

        if (dateFilter) {
            (params as any)["filter"] = `timestamp:GTE:'${dateFilter}'`;
        }

        return this.http.get(url, {observe: "response", params}).pipe(
            map((res: HttpResponse<LogEntry[]>) => {
                return {
                    count: parseInt(res.headers.get("X-Total-Count"), 10),
                    entities: res.body
                };
            }));
    }

}
