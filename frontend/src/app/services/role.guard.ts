import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";
import {GuardRoles} from "../models/auth.models";

@Injectable({
    providedIn: "root"
})
export class RoleGuard implements CanActivate {

    constructor(private auth: AuthService, private router: Router) {

    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

        if (!this.auth.isAuthenticated()) {
            this.auth.redirectToLogin();
            return false;
        }

        const data = route.data as GuardRoles;

        if (data.requiredRoles && data.requiredRoles.length > 0) {

            const ownedRequiredRoles = data.requiredRoles.filter(role => {
                return this.auth.hasRole(role);
            });

            if (ownedRequiredRoles.length > 0) {
                return true;
            } else {
                this.router.navigate(["/403"]);
                return false;
            }
        }
        return true;
    }

}
