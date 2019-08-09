import {Inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {DockerDaemon} from "../models/docker-daemon";
import {map} from "rxjs/operators";
import {API_URL} from "../injectables";

@Injectable({
    providedIn: "root"
})
export class DockerDaemonService {

    constructor(
        @Inject(API_URL) private apiUrl: string,
        private http: HttpClient) {

    }

    public getDaemons(): Observable<DockerDaemon[]> {
        return this.http.get(`${this.apiUrl}/docker-daemons`).pipe(map(res => res as DockerDaemon[]));
    }

    public addDaemon(daemon: DockerDaemon): Observable<DockerDaemon> {
        return this.http.post(`${this.apiUrl}/docker-daemons`, JSON.stringify(daemon)).pipe(map(res => res as DockerDaemon));
    }

    public updateDaemon(daemon: DockerDaemon): Observable<DockerDaemon> {
        const url = `${this.apiUrl}/docker-daemons/${daemon.id}`;
        return this.http.put(url, JSON.stringify(daemon)).pipe(map(res => res as DockerDaemon));
    }

    public removeDaemon(daemonId: number): Observable<void> {
        const url = `${this.apiUrl}/docker-daemons/${daemonId}`;
        return this.http.delete(url).pipe(map(res => null));
    }
}
