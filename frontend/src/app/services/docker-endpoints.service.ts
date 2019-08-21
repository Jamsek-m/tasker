import {Inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {DockerEndpoint} from "../models/docker-endpoint.model";
import {map} from "rxjs/operators";
import {API_URL} from "../injectables";

@Injectable({
    providedIn: "root"
})
export class DockerEndpointsService {

    constructor(
        @Inject(API_URL) private apiUrl: string,
        private http: HttpClient) {

    }

    public getEndpoints(): Observable<DockerEndpoint[]> {
        return this.http.get(`${this.apiUrl}/docker-endpoints`).pipe(map(res => res as DockerEndpoint[]));
    }

    public addEndpoint(daemon: DockerEndpoint): Observable<DockerEndpoint> {
        return this.http.post(`${this.apiUrl}/docker-endpoints`, JSON.stringify(daemon)).pipe(map(res => res as DockerEndpoint));
    }

    public updateEndpoint(daemon: DockerEndpoint): Observable<DockerEndpoint> {
        const url = `${this.apiUrl}/docker-endpoints/${daemon.id}`;
        return this.http.put(url, JSON.stringify(daemon)).pipe(map(res => res as DockerEndpoint));
    }

    public removeEndpoint(daemonId: number): Observable<void> {
        const url = `${this.apiUrl}/docker-endpoints/${daemonId}`;
        return this.http.delete(url).pipe(map(() => null));
    }
}
