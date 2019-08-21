import {Component, OnInit} from "@angular/core";
import {MenuItem, menuItems} from "../../content/menu-items";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {ActionsService} from "../../services/actions.service";

@Component({
    selector: "tasker-header",
    templateUrl: "./header.component.html",
    styleUrls: ["./header.component.scss"]
})
export class HeaderComponent implements OnInit {

    public navbarOpened = false;
    public menuItems: MenuItem[] = menuItems;
    public isAuthorized = false;

    constructor(private router: Router,
                private authService: AuthService,
                private actionService: ActionsService) {
    }

    ngOnInit() {
        this.isAuthorized = this.authService.isAuthenticated();
        this.checkNotifications();
    }

    public toggleNavbar() {
        this.navbarOpened = !this.navbarOpened;
    }

    public logout() {
        this.authService.logout();
    }

    public login() {
        this.authService.login();
    }

    public hasRole(menuItem: MenuItem): boolean {
        if (!menuItem.requiredRoles) {
            return true;
        }

        return menuItem.requiredRoles.filter(role => {
            return this.authService.hasRealmRole(role);
        }).length > 0;
    }

    private checkNotifications(): void {
        this.actionService.checkServerReadiness().subscribe(
            () => {

            }
        );
    }

}
