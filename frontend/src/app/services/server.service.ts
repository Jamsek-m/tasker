import {Inject, Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {API_URL} from "../injectables";
import {Observable} from "rxjs";
import {EntityList} from "../models/common/dto.model";
import {Server} from "../models/server.class";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: "root"
})
export class ServerService {

    constructor(
        @Inject(API_URL) private apiUrl: string,
        private http: HttpClient) {
    }

    public getServers(limit: number, offset: number): Observable<EntityList<Server>> {
        const url = `${this.apiUrl}/servers`;
        const params = {
            limit: limit.toString(10),
            offset: offset.toString(10)
        };
        return this.http.get(url, {observe: "response", params}).pipe(
            map((res: HttpResponse<Server[]>) => {
                return {
                    count: parseInt(res.headers.get("X-Total-Count"), 10),
                    entities: res.body
                };
            })
        );
    }

    public updateServer(server: Server): Observable<Server> {
        const url = `${this.apiUrl}/servers/${server.id}`;
        return this.http.patch(url, JSON.stringify(server)).pipe(
            map(res => res as Server)
        );
    }

    public addServer(server: Server): Observable<Server> {
        const url = `${this.apiUrl}/servers`;
        return this.http.post(url, JSON.stringify(server)).pipe(map(res => res as Server));
    }

    public deleteServer(serverId: string): Observable<void> {
        const url = `${this.apiUrl}/servers/${serverId}`;
        return this.http.delete(url).pipe(map(() => null));
    }

}
