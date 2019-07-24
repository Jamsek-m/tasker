import {EventEmitter, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {UserDTO} from "../models/user.dto";
import {Observable, of} from "rxjs";
import {environment} from "../../environments/environment";
import {catchError, map, tap} from "rxjs/operators";
import {User} from "../models/user.class";


@Injectable({
    providedIn: "root"
})
export class AuthService {

    private apiUrl = `${environment.apiUrl}/auth`;

    private eventEmitter: EventEmitter<string|null> = new EventEmitter<string|null>();

    constructor(private http: HttpClient) {

    }

    public loginUser(user: UserDTO): Observable<void> {
        const url = `${this.apiUrl}/login`;
        return this.http.post(url, JSON.stringify(user)).pipe(
            tap(() => {
                this.registerAuthEvent(user.username);
            }),
            map(() => null)
        );
    }

    public logoutUser(): Observable<void> {
        const url = `${this.apiUrl}/logout`;
        return this.http.get(url).pipe(
            tap(() => {
                this.registerAuthEvent(null);
            }),
            map(() => null)
        );
    }

    public checkAuthorization(): Observable<boolean> {
        const url = `${this.apiUrl}/is-authorized`;
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
        const url = `${this.apiUrl}/user/${dto.id}`;
        return this.http.put(url, JSON.stringify(dto)).pipe(map(() => null));
    }

    public getCurrentUser(): Observable<User> {
        const url = `${this.apiUrl}/current-user`;
        return this.http.get(url).pipe(map(res => res as User));
    }

    public registerAuthEvent(username?: string): void {
        this.eventEmitter.emit(username);
    }

    public getAuthEvent(): EventEmitter<string | null> {
        return this.eventEmitter;
    }
}
