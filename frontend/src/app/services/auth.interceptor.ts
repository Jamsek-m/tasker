import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {from, Observable} from "rxjs";
import {AuthService} from "./auth.service";
import {switchMap} from "rxjs/operators";


@Injectable({
    providedIn: "root"
})
export class AuthInterceptor implements HttpInterceptor {

    constructor(private auth: AuthService) {

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.auth.isAuthenticated()) {
            return from(new Promise((resolve, reject) => {
                this.auth.refreshToken().then(() => {
                    resolve();
                }).catch(() => {
                    this.auth.logout();
                    reject();
                });
            })).pipe(
                switchMap(() => {
                    const token = this.auth.getToken();
                    let headers = req.headers;
                    if (token !== null) {
                        headers = headers.set("Authorization", `Bearer ${token}`);
                    }

                    return next.handle(req.clone({
                        headers
                    }));
                })
            );
        }
        return next.handle(req);
    }

}
