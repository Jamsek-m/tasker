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
import {EntityList} from "../models/common/dto.model";
import {HealthCheckResponse} from "../models/enums/healthcheck-response.enum";
import {UrlUtil} from "../utils/url.util";

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
        const url = `${this.apiUrl}/services`;
        const params = {
            filter: `name:LIKEIC:'%${name}%'`,
            limit: "5"
        };
        return this.http.get(url, {params}).pipe(map(res => res as Service[]));
    }

    public getService(serviceId: string): Observable<Service> {
        const url = `${this.apiUrl}/services/${serviceId}`;
        return this.http.get(url).pipe(
            map(res => res as Service)
        );
    }

    public doHealthCheck(serviceId: string): Observable<HealthCheckResponse> {
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
        if (!entity.name) {
            validation.validEntity = false;
            validation.name = "Name must not be empty!";
        }

        if (!entity.version) {
            validation.validEntity = false;
            validation.version = "Version must not be empty!";
        }

        if (!entity.type) {
            validation.validEntity = false;
            validation.type = "Type must not be null!";
        }

        if (entity.deployment && ObjectUtil.isEmpty(entity.deployment.dockerEndpoint)) {
            validation.validEntity = false;
            validation.dockerEndpoint = "Please choose an endpoint.";
        }
        if (entity.deployment && !entity.deployment.containerName) {
            validation.validEntity = false;
            validation.containerName = "Container name must not be empty!";
        }
        if (entity.deployment && !entity.deployment.containerId) {
            validation.validEntity = false;
            validation.containerId = "Container ID must not be empty!";
        }

        if (entity.type === "CLIENT_APP" || entity.type === "WEB_APP") {

            entity.applicationUrl = UrlUtil.buildUrl(entity.applicationUrl);
            if (!entity.applicationUrl) {
                validation.validEntity = false;
                validation.applicationUrl = "Application url must not be null and must be valid url!";
            }
        }

        if (entity.type === "API_SERVICE" || entity.type === "WEB_APP") {
            entity.healthcheckUrl = UrlUtil.buildUrl(entity.healthcheckUrl);
            entity.baseUrl = UrlUtil.buildUrl(entity.baseUrl);
            if (!entity.baseUrl) {
                validation.validEntity = false;
                validation.baseUrl = "Base url must not be null and must be valid url!";
            }

            if (!entity.majorVersion) {
                validation.validEntity = false;
                validation.majorVersion = "Major version must not be null!";
            }
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

    public removeService(serviceId: string): Observable<void> {
        const url = `${this.apiUrl}/services/${serviceId}`;
        return this.http.delete(url).pipe(map(() => null));
    }

}
