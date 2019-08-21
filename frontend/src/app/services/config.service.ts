import {Inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {ConfigEntry} from "../models/config-entry.model";
import {map} from "rxjs/operators";
import {API_URL} from "../injectables";


@Injectable({
    providedIn: "root"
})
export class ConfigService {

    constructor(
        @Inject(API_URL) private apiUrl: string,
        private http: HttpClient) {

    }

    public getConfiguration(): Observable<ConfigEntry[]> {
        return this.http.get(`${this.apiUrl}/config`).pipe(map(res => res as ConfigEntry[]));
    }

    public addConfiguration(config: ConfigEntry): Observable<void> {
        return this.http.post(`${this.apiUrl}/config`, JSON.stringify(config)).pipe(map(() => null));
    }

    public updateConfiguration(config: ConfigEntry): Observable<void> {
        const url = `${this.apiUrl}/config/${config.id}`;
        return this.http.put(url, JSON.stringify(config)).pipe(map(() => null));
    }

    public deleteConfiguration(configId: number): Observable<void> {
        const url = `${this.apiUrl}/config/${configId}`;
        return this.http.delete(url).pipe(map(() => null));
    }

}
