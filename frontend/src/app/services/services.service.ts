import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, of, throwError} from "rxjs";
import {Service, ServiceValidation} from "../models/service.class";
import {ServiceDTO} from "../models/service.dto";
import {catchError, map} from "rxjs/operators";
import {StringUtil} from "../utils/string.util";
import {ObjectUtil} from "../utils/object.util";
import {ConflictError} from "../errors/conflict.error";
import {ValidationError} from "../errors/validation.error";
import {UnknownError} from "../errors/unknown.error";
import {InternalServerError} from "../errors/server.error";

export enum HealthCheckResponse {
    OK,
    ERROR,
    UNDEF
}

@Injectable({
    providedIn: "root"
})
export class ServicesService {

    private v2Api = `${environment.apiUrl}/services`;

    constructor(private http: HttpClient) {

    }

    public getServices(limit: number, offset: number): Observable<ServiceDTO> {
        const url = `${this.v2Api}?offset=${offset}&limit=${limit}`;
        return this.http.get(url, {observe: "response"}).pipe(map(res => {
            const dto = new ServiceDTO();
            dto.totalCount = parseInt(res.headers.get("X-Total-Count"), 10);
            dto.services = res.body as Service[];
            return dto;
        }));
    }

    public queryServicesByName(name: string): Observable<Service[]> {
        const url = `${this.v2Api}?filter=name:LIKEIC:%${name}%&limit=5`;
        return this.http.get(url).pipe(map(res => res as Service[]));
    }

    public getService(serviceId: number): Observable<Service> {
        const url = `${this.v2Api}/${serviceId}`;
        return this.http.get(url).pipe(
            map(res => res as Service)
        );
    }

    public doHealthCheck(serviceId: number): Observable<HealthCheckResponse> {
        const url = `${this.v2Api}/${serviceId}/health`;
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
        const urlRegex = /^https?:\/\/.+/g;

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
        if (entity.hasHealthcheck && entity.healthCheck.healthUrl) {
            if (!urlRegex.test(entity.healthCheck.healthUrl)) {
                validation.validEntity = false;
                validation.healthUrl = "Must be valid URL!";
            }
        } else if (entity.hasHealthcheck && !entity.healthCheck.healthUrl) {
            validation.validEntity = false;
            validation.healthUrl = "Healthcheck url must not be empty!";
        }
        if (entity.isDeployed && !entity.serviceUrl.version) {
            validation.validEntity = false;
            validation.ApiVersion = "API version must not be empty!";
        }
        console.log(`validating service url: >${entity.serviceUrl.url}<. Does url contain common part: ${urlRegex.test(entity.serviceUrl.url)}`);
        if (entity.isDeployed && entity.serviceUrl.url) {
            if (!urlRegex.test(entity.serviceUrl.url)) {
                validation.validEntity = false;
                validation.url = "Must be valid URL!";
            }
        } else if (entity.isDeployed && !entity.serviceUrl.url) {
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
        return this.http.post(this.v2Api, data).pipe(
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
        const url = `${this.v2Api}/${service.id}`;
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
        const url = `${this.v2Api}/${serviceId}/token`;
        return this.http.patch(url, null).pipe(map(res => res as Service.Token));
    }

    public removeService(serviceId: number): Observable<void> {
        const url = `${this.v2Api}/${serviceId}`;
        return this.http.delete(url).pipe(map(res => null));
    }

}
