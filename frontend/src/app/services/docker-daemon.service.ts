import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {DockerDaemon} from "../models/docker-daemon";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: "root"
})
export class DockerDaemonService {

    private v2Api = `${environment.apiUrl}/docker-daemons`;

    constructor(private http: HttpClient) {

    }

    public getDaemons(): Observable<DockerDaemon[]> {
        return this.http.get(this.v2Api).pipe(map(res => res as DockerDaemon[]));
    }

    public addDaemon(daemon: DockerDaemon): Observable<DockerDaemon> {
        return this.http.post(this.v2Api, JSON.stringify(daemon)).pipe(map(res => res as DockerDaemon));
    }

    public updateDaemon(daemon: DockerDaemon): Observable<DockerDaemon> {
        const url = `${this.v2Api}/${daemon.id}`;
        return this.http.put(url, JSON.stringify(daemon)).pipe(map(res => res as DockerDaemon));
    }

    public removeDaemon(daemonId: number): Observable<void> {
        const url = `${this.v2Api}/${daemonId}`;
        return this.http.delete(url).pipe(map(res => null));
    }
}
