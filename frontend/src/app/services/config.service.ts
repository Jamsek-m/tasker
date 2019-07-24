import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {ConfigEntry} from "../models/config-entry.class";
import {map} from "rxjs/operators";


@Injectable({
    providedIn: "root"
})
export class ConfigService {

    private apiUrl = `${environment.apiUrl}/config`;

    constructor(private http: HttpClient) {

    }

    public getConfiguration(): Observable<ConfigEntry[]> {
        return this.http.get(this.apiUrl).pipe(map(res => res as ConfigEntry[]));
    }

    public addConfiguration(config: ConfigEntry): Observable<void> {
        return this.http.post(this.apiUrl, JSON.stringify(config)).pipe(map(() => null));
    }

    public updateConfiguration(config: ConfigEntry): Observable<void> {
        const url = `${this.apiUrl}/${config.id}`;
        return this.http.put(url, JSON.stringify(config)).pipe(map(() => null));
    }

    public deleteConfiguration(configId: number): Observable<void> {
        const url = `${this.apiUrl}/${configId}`;
        return this.http.delete(url).pipe(map(() => null));
    }

}
