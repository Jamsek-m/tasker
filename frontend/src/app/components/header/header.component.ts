import {Component, OnInit} from "@angular/core";
import {MenuItem, menuItems} from "../../content/menu-items";
import {Router} from "@angular/router";
import {ActionsService} from "../../services/actions.service";
import {KeycloakService, ExtendedKeycloakTokenPayload} from "@mjamsek/ngx-keycloak-service";

@Component({
    selector: "tasker-header",
    templateUrl: "./header.component.html",
    styleUrls: ["./header.component.scss"]
})
export class HeaderComponent implements OnInit {

    public navbarOpened = false;
    public menuItems: MenuItem[] = menuItems;
    public isAuthorized = false;
    public username: string = null;
    public email: string = null;

    constructor(private router: Router,
                private keycloakService: KeycloakService,
                private actionService: ActionsService) {
    }

    ngOnInit() {
        this.isAuthorized = this.keycloakService.isAuthenticated();
        if (this.isAuthorized) {
            const payload: ExtendedKeycloakTokenPayload = this.keycloakService.getTokenPayload<ExtendedKeycloakTokenPayload>();
            this.username = payload.preferred_username;
            if (payload.email_verified) {
                this.email = payload.email;
            }
        }
        // this.checkNotifications();
    }

    public toggleNavbar() {
        this.navbarOpened = !this.navbarOpened;
    }

    public logout() {
        this.keycloakService.logout();
    }

    public login() {
        this.keycloakService.redirectToLogin();
    }

    public hasRole(menuItem: MenuItem): boolean {

        if (KeycloakService.configuration.minimalRequiredRole) {
            if (!this.keycloakService.hasRole(KeycloakService.configuration.minimalRequiredRole)) {
                return false;
            }
        }

        if (!menuItem.requiredRoles) {
            return true;
        }

        return menuItem.requiredRoles.filter(role => {
            return this.keycloakService.hasRealmRole(role);
        }).length > 0;
    }

    private checkNotifications(): void {
        this.actionService.checkServerReadiness().subscribe(
            () => {

            }
        );
    }

}
