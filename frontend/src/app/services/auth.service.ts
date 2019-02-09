import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {UserDTO} from "../models/user.dto";
import {Observable, of} from "rxjs";
import {environment} from "../../environments/environment";
import {catchError, map} from "rxjs/operators";
import {User} from "../models/user.class";


@Injectable({
    providedIn: "root"
})
export class AuthService {

    private apiUrl = `${environment.apiUrl}/auth`;

    constructor(private http: HttpClient) {

    }

    public loginUser(user: UserDTO): Observable<void> {
        const url = `${this.apiUrl}/login`;
        return this.http.post(url, JSON.stringify(user)).pipe(map(() => Observable.create()));
    }

    public logoutUser(): Observable<void> {
        const url = `${this.apiUrl}/logout`;
        return this.http.get(url).pipe(map(() => Observable.create()));
    }

    public isLoggedIn(): Observable<boolean> {
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
        return this.http.put(url, JSON.stringify(dto)).pipe(map(() => Observable.create()));
    }

    public getCurrentUser(): Observable<User> {
        const url = `${this.apiUrl}/current-user`;
        return this.http.get(url).pipe(map(res => res as User));
    }
}
