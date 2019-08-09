import {Inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AvailableActions} from "../models/available-actions.class";
import {map} from "rxjs/operators";
import {API_URL, BASE_URL} from "../injectables";

@Injectable({
    providedIn: "root"
})
export class ActionsService {

    constructor(
        @Inject(API_URL) private apiUrl: string,
        @Inject(BASE_URL) private baseUrl: string,
        private http: HttpClient) {
    }

    public getAvailableActions(): Observable<AvailableActions> {
        return this.http.get("").pipe(map(res => res as AvailableActions));
    }

    public checkServerReadiness(): Observable<any> {
        const url = `${this.baseUrl}/health/ready`;
        return this.http.get(url);
    }
}
