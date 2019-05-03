import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {DockerDaemon} from "../models/docker-daemon";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Service} from "../models/service.class";


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

}
