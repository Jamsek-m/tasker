import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AvailableActions} from "../models/available-actions.class";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: "root"
})
export class ActionsService {

    private apiUrl = `${environment.apiUrl}/actions`;

    constructor(private http: HttpClient) {
    }

    public getAvailableActions(): Observable<AvailableActions> {
        return this.http.get(this.apiUrl).pipe(map(res => res as AvailableActions));
    }
}
