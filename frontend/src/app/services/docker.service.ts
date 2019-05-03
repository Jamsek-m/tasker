import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {DockerDaemon} from "../models/docker-daemon";
import {Observable, of} from "rxjs";
import {environment} from "../../environments/environment";
import {Service} from "../models/service.class";
import {DockerState} from "../models/docker-state.class";
import {map} from "rxjs/operators";


@Injectable({
    providedIn: "root"
})
export class DockerService {

    private apiUrl = `${environment.apiUrl}/docker`;
    private serviceApiUrl = `${environment.apiUrl}/services`;

    constructor(private http: HttpClient) {

    }

    public searchContainerByName(name: string, daemon: DockerDaemon): Observable<any> {
        return this.http.get(this.apiUrl, {
            params: {
                name,
                daemonId: daemon.id.toString(10)
            }
        });
    }

    public getContainerInfo(service: Service): Observable<any> {
        const url = `${this.serviceApiUrl}/${service.id}/container`;
        return this.http.get(url, {
            params: {
                raw: "true"
            }
        });
    }

    public getContainerState(service: Service): Observable<DockerState> {
        const url = `${this.serviceApiUrl}/${service.id}/container/state`;
        return this.http.get(url).pipe(map(res => res as DockerState));
    }

    public startContainer(service: Service): Observable<void> {
        const url = `${this.serviceApiUrl}/${service.id}/container/start`;
        return this.http.post(url, null).pipe(map(() => null));
    }

    public stopContainer(service: Service): Observable<void> {
        const url = `${this.serviceApiUrl}/${service.id}/container/stop`;
        return this.http.delete(url).pipe(map(() => null));
    }

    public recreateContainer(service: Service): Observable<void> {
        const url = `${this.serviceApiUrl}/${service.id}/container/recreate`;
        return this.http.put(url, null).pipe(map(() => null));
    }

}
