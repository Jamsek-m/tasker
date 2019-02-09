import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Token} from "../models/token.class";
import {map} from "rxjs/operators";
import {TokenDTO} from "../models/token.dto";
import {TokenKeyDTO} from "../models/token-key.dto";

@Injectable({
    providedIn: "root"
})
export class TokenService {

    private apiUrl = `${environment.apiUrl}/tokens`;

    constructor(private http: HttpClient) {
    }

    public getTokens(limit: number, offset: number): Observable<TokenDTO> {
        const url = `${this.apiUrl}?offset=${offset}&limit=${limit}`;
        return this.http.get(url, {observe: "response"}).pipe(map(res => {
            const dto = new TokenDTO();
            dto.totalCount = parseInt(res.headers.get("X-Total-Count"), 10);
            dto.tokens = res.body as Token[];
            return dto;
        }));
    }

    public expireToken(tokenId: number): Observable<void> {
        const url = `${this.apiUrl}/${tokenId}/expire`;
        return this.http.delete(url).pipe(map(() => Observable.create()));
    }

    public saveToken(token: Token): Observable<TokenKeyDTO> {
        return this.http.post(this.apiUrl, JSON.stringify(token)).pipe(map(res => res as TokenKeyDTO));
    }
}
