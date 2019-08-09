import {EventEmitter, Inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {UserDTO} from "../models/user.dto";
import {Observable, of} from "rxjs";
import {catchError, map, tap} from "rxjs/operators";
import {User} from "../models/user.class";
import {API_URL} from "../injectables";

@Injectable({
    providedIn: "root"
})
export class AuthService {

    private eventEmitter: EventEmitter<string | null> = new EventEmitter<string | null>();

    constructor(
        @Inject(API_URL) private apiUrl: string,
        private http: HttpClient) {

    }

    public loginUser(user: UserDTO): Observable<void> {
        const url = `${this.apiUrl}/auth/login`;
        return this.http.post(url, JSON.stringify(user)).pipe(
            tap(() => {
                this.registerAuthEvent(user.username);
            }),
            map(() => null)
        );
    }

    public logoutUser(): Observable<void> {
        const url = `${this.apiUrl}/auth/logout`;
        return this.http.get(url).pipe(
            tap(() => {
                this.registerAuthEvent(null);
            }),
            map(() => null)
        );
    }

    public checkAuthorization(): Observable<boolean> {
        const url = `${this.apiUrl}/auth/is-authorized`;
        return this.http.get(url, {observe: "response"}).pipe(
            map(() => {
                return true;
            }),
            catchError(() => {
                return of(false);
            })
        );
    }

    public changePassword(dto: User): Observable<void> {
        const url = `${this.apiUrl}/auth/user/${dto.id}`;
        return this.http.put(url, JSON.stringify(dto)).pipe(map(() => null));
    }

    public getCurrentUser(): Observable<User> {
        const url = `${this.apiUrl}/auth/current-user`;
        return this.http.get(url).pipe(map(res => res as User));
    }

    public registerAuthEvent(username?: string): void {
        this.eventEmitter.emit(username);
    }

    public getAuthEvent(): EventEmitter<string | null> {
        return this.eventEmitter;
    }
}
