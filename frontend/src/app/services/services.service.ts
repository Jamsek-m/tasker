import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, of} from "rxjs";
import {Service} from "../models/service.class";
import {ServiceDTO} from "../models/service.dto";
import {catchError, map} from "rxjs/operators";

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

    public getService(serviceId: number): Observable<Service> {
        const url = `${this.v2Api}/${serviceId}`;
        return this.http.get(url).pipe(map(res => res as Service));
    }

    public doHealthCheck(serviceId: number): Observable<HealthCheckResponse> {
        const url = `${this.v2Api}/${serviceId}/health`;
        return this.http.get(url, {observe: "response"}).pipe(
            map(res => HealthCheckResponse.OK),
            catchError((err: HttpErrorResponse) => {
                if (err.status === 417) {
                    return of (HealthCheckResponse.UNDEF);
                }
                return of(HealthCheckResponse.ERROR);
            })
        );
    }

}
