import {Inject, Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {API_URL} from "../injectables";
import {Observable} from "rxjs";
import {Domain} from "../models/domain.model";
import {EntityList} from "../models/common/dto.model";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: "root"
})
export class DomainService {

    constructor(private http: HttpClient,
                @Inject(API_URL) private apiUrl: string) {

    }

    public getDomains(limit: number, offset: number): Observable<EntityList<Domain>> {
        const url = `${this.apiUrl}/domains`;
        const params = {
            limit: limit.toString(10),
            offset: offset.toString(10)
        };
        return this.http.get(url, {observe: "response", params}).pipe(
            map((res: HttpResponse<Domain[]>) => {
                return {
                    count: parseInt(res.headers.get("X-Total-Count"), 10),
                    entities: res.body
                };
            })
        );
    }

    public updateDomain(domain: Domain): Observable<Domain> {
        const url = `${this.apiUrl}/domains/${domain.id}`;
        return this.http.patch(url, JSON.stringify(domain)).pipe(
            map(res => res as Domain)
        );
    }


}
