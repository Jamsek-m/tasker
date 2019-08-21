import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";
import {map} from "rxjs/operators";


@Injectable({
    providedIn: "root"
})
export class AuthGuard implements CanActivate {

    constructor(private auth: AuthService, private router: Router) {

    }

    public canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        if (this.auth.isAuthenticated()) {
            return true;
        } else {
            this.auth.redirectToLogin();
            return false;
        }
    }
}
