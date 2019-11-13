import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {KeycloakService} from "@mjamsek/ngx-keycloak-service";

@Component({
    selector: "tasker-auth-redirect-page",
    template: `<div class="auth-redirect"></div>`,
})
export class AuthRedirectComponent implements OnInit {

    constructor(private keycloakService: KeycloakService, private router: Router) {

    }

    public ngOnInit(): void {
        if (this.keycloakService.isAuthenticated()) {
            this.router.navigate(["/"]);
        } else {
            this.keycloakService.redirectToLogin();
        }
    }
}
