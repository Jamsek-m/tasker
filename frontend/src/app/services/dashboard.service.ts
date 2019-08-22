import {Inject, Injectable} from "@angular/core";
import {API_URL} from "../injectables";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Statistics} from "../models/statistics.model";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: "root"
})
export class DashboardService {

    constructor(@Inject(API_URL) private apiUrl: string,
                private http: HttpClient) {

    }

    public getStatistics(): Observable<Statistics> {
        const url = `${this.apiUrl}/dashboard/statistics`;
        return this.http.get(url).pipe(map(res => res as Statistics));
    }

}
