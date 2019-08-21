import {Inject, Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable, of, throwError} from "rxjs";
import {Service, ServiceValidation} from "../models/service.class";
import {catchError, map} from "rxjs/operators";
import {StringUtil} from "../utils/string.util";
import {ObjectUtil} from "../utils/object.util";
import {ConflictError} from "../errors/conflict.error";
import {ValidationError} from "../errors/validation.error";
import {UnknownError} from "../errors/unknown.error";
import {InternalServerError} from "../errors/server.error";
import {API_URL} from "../injectables";
import {UrlUtil} from "../utils/url.util";
import {EntityList} from "../models/common/dto.model";

export enum HealthCheckResponse {
    OK,
    ERROR,
    UNDEF
}

@Injectable({
    providedIn: "root"
})
export class ServicesService {

    constructor(
        @Inject(API_URL) private apiUrl: string,
        private http: HttpClient) {

    }

    public getServices(limit: number, offset: number): Observable<EntityList<Service>> {
        const url = `${this.apiUrl}/services`;
        const params = {
            offset: offset.toString(10),
            limit: limit.toString(10)
        };
        return this.http.get(url, {observe: "response", params}).pipe(
            map((res: HttpResponse<Service[]>) => {
                return {
                    count: parseInt(res.headers.get("X-Total-Count"), 10),
                    entities: res.body
                };
            }));
    }

    public queryServicesByName(name: string): Observable<Service[]> {
        const url = `${this.apiUrl}/services?filter=name:LIKEIC:%${name}%&limit=5`;
        return this.http.get(url).pipe(map(res => res as Service[]));
    }

    public getService(serviceId: number): Observable<Service> {
        const url = `${this.apiUrl}/services/${serviceId}`;
        return this.http.get(url).pipe(
            map(res => res as Service)
        );
    }

    public doHealthCheck(serviceId: number): Observable<HealthCheckResponse> {
        const url = `${this.apiUrl}/services/${serviceId}/health`;
        return this.http.get(url, {observe: "response"}).pipe(
            map(() => HealthCheckResponse.OK),
            catchError((err: HttpErrorResponse) => {
                if (err.status === 417) {
                    return of(HealthCheckResponse.UNDEF);
                }
                return of(HealthCheckResponse.ERROR);
            })
        );
    }

    public validateService(entity: Service): ServiceValidation {
        const validation = new ServiceValidation();

        StringUtil.trimAllProperties(entity);

        const nameRegex = /[^a-z0-9_\-]/g;
        if (nameRegex.test(entity.name)) {
            validation.validEntity = false;
            validation.name = "Name can contain only lowercase alphanumeric symbols, underscore and dash!";
        }

        if (!entity.version) {
            validation.validEntity = false;
            validation.version = "Version must not be empty!";
        }
        if (!entity.name) {
            validation.validEntity = false;
            validation.name = "Name must not be empty!";
        }

        entity.healthCheck.healthUrl = UrlUtil.buildUrl(entity.healthCheck.healthUrl);
        if (entity.hasHealthcheck && !entity.healthCheck.healthUrl) {
            validation.validEntity = false;
            validation.healthUrl = "Healthcheck url must not be empty!";
        }
        if (entity.isDeployed && entity.serviceUrl.urlVersioning && !entity.serviceUrl.version) {
            validation.validEntity = false;
            validation.ApiVersion = "API version must not be empty!";
        }

        entity.serviceUrl.url = UrlUtil.buildUrl(entity.serviceUrl.url);
        if (entity.isDeployed && !entity.serviceUrl.url) {
            validation.validEntity = false;
            validation.url = "Base URL must not be empty!";
        }
        if (entity.isDockerized && ObjectUtil.isEmpty(entity.deployment.dockerDaemon)) {
            validation.validEntity = false;
            validation.dockerDaemon = "Please choose a daemon.";
        }
        if (entity.isDockerized && !entity.deployment.containerName) {
            validation.validEntity = false;
            validation.containerName = "Container name must not be empty!";
        }
        if (entity.isDockerized && !entity.deployment.containerId) {
            validation.validEntity = false;
            validation.containerId = "Container ID must not be empty!";
        }

        return validation;
    }

    public createService(service: Service): Observable<Service> {
        const data = JSON.stringify(service);
        return this.http.post(`${this.apiUrl}/services`, data).pipe(
            map(res => res as Service),
            catchError((err: HttpErrorResponse) => {
                if (err.status === 409) {
                    return throwError(new ConflictError(err.error.message));
                } else if (err.status === 422) {
                    return throwError(new ValidationError(err.error.message));
                } else if (err.status === 500) {
                    return throwError(new InternalServerError(err.error.message));
                } else {
                    return throwError(new UnknownError());
                }
            })
        );
    }

    public updateService(service: Service): Observable<Service> {
        const url = `${this.apiUrl}/services/${service.id}`;
        const data = JSON.stringify(service);
        return this.http.put(url, data).pipe(
            map(res => res as Service),
            catchError((err: HttpErrorResponse) => {
                if (err.status === 409) {
                    return throwError(new ConflictError(err.error.message));
                } else if (err.status === 422) {
                    return throwError(new ValidationError(err.error.message));
                } else if (err.status === 500) {
                    return throwError(new InternalServerError(err.error.message));
                } else {
                    return throwError(new UnknownError());
                }
            })
        );
    }

    public generateServiceToken(serviceId: number): Observable<Service.Token> {
        const url = `${this.apiUrl}/services/${serviceId}/token`;
        return this.http.patch(url, null).pipe(map(res => res as Service.Token));
    }

    public removeService(serviceId: number): Observable<void> {
        const url = `${this.apiUrl}/services/${serviceId}`;
        return this.http.delete(url).pipe(map(res => null));
    }

}
