import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
    selector: "tasker-auth-redirect-page",
    template: `<div class="auth-redirect"></div>`,
})
export class AuthRedirectComponent implements OnInit {

    constructor(private auth: AuthService, private router: Router) {

    }

    public ngOnInit(): void {
        if (this.auth.isAuthenticated()) {
            this.router.navigate(["/"]);
        } else {
            this.auth.redirectToLogin();
        }
    }
}
