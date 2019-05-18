import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {DockerDaemon} from "../models/docker-daemon";
import {Observable, of, throwError} from "rxjs";
import {environment} from "../../environments/environment";
import {Service} from "../models/service.class";
import {DockerState} from "../models/docker-state.class";
import {catchError, map} from "rxjs/operators";
import {DockerPartialInfo} from "../models/docker-partial-info.interface";
import {InternalServerError} from "../errors/server.error";
import {UnknownError} from "../errors/unknown.error";
import {NotFoundError} from "../errors/not-found.error";


@Injectable({
    providedIn: "root"
})
export class DockerService {

    private apiUrl = `${environment.apiUrl}/docker`;
    private serviceApiUrl = `${environment.apiUrl}/services`;

    constructor(private http: HttpClient) {

    }

    public searchContainerByName(name: string, daemon: DockerDaemon): Observable<DockerPartialInfo[]> {
        return this.http.get(this.apiUrl, {
            params: {
                name,
                daemonId: daemon.id.toString(10)
            }
        }).pipe(
            map(res => res as DockerPartialInfo[]),
            catchError((err: HttpErrorResponse) => {
                if (err.status === 500) {
                    return throwError(new InternalServerError(err.message));
                }
                console.error(err);
                return throwError(new UnknownError());
            })
        );
    }

    public getContainerInfo(service: Service): Observable<any> {
        const url = `${this.serviceApiUrl}/${service.id}/container`;
        return this.http.get(url, {
            params: {
                raw: "true"
            }
        }).pipe(
            map(res => res as DockerPartialInfo[]),
            catchError((err: HttpErrorResponse) => {
                if (err.status === 500) {
                    return throwError(new InternalServerError(err.message));
                } else if (err.status === 404) {
                    return throwError(new NotFoundError(err.message));
                }
                console.error(err);
                return throwError(new UnknownError());
            })
        );
    }

    public getContainerState(service: Service): Observable<DockerState> {
        const url = `${this.serviceApiUrl}/${service.id}/container/state`;
        return this.http.get(url).pipe(
            map(res => res as DockerState),
            catchError((err: HttpErrorResponse) => {
                if (err.status === 500) {
                    return throwError(new InternalServerError(err.message));
                } else if (err.status === 404) {
                    return throwError(new NotFoundError(err.message));
                }
                console.error(err);
                return throwError(new UnknownError());
            })
        );
    }

    public startContainer(service: Service): Observable<void> {
        const url = `${this.serviceApiUrl}/${service.id}/container/start`;
        return this.http.post(url, null).pipe(
            map(() => null),
            catchError((err: HttpErrorResponse) => {
                if (err.status === 500) {
                    return throwError(new InternalServerError(err.message));
                } else if (err.status === 404) {
                    return throwError(new NotFoundError(err.message));
                }
                console.error(err);
                return throwError(new UnknownError());
            })
        );
    }

    public stopContainer(service: Service): Observable<void> {
        const url = `${this.serviceApiUrl}/${service.id}/container/stop`;
        return this.http.delete(url).pipe(
            map(() => null),
            catchError((err: HttpErrorResponse) => {
                if (err.status === 500) {
                    return throwError(new InternalServerError(err.message));
                } else if (err.status === 404) {
                    return throwError(new NotFoundError(err.message));
                }
                console.error(err);
                return throwError(new UnknownError());
            })
        );
    }

    public recreateContainer(service: Service): Observable<void> {
        const url = `${this.serviceApiUrl}/${service.id}/container/recreate`;
        return this.http.put(url, null).pipe(
            map(() => null),
            catchError((err: HttpErrorResponse) => {
                if (err.status === 500) {
                    return throwError(new InternalServerError(err.message));
                } else if (err.status === 404) {
                    return throwError(new NotFoundError(err.message));
                }
                console.error(err);
                return throwError(new UnknownError());
            })
        );
    }

}
