import {Component, OnInit} from "@angular/core";
import {MenuItem, menuItems} from "../../content/menu-items";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

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
                private authService: AuthService) {
    }

    ngOnInit() {
        this.checkAuth();
        this.authService.getAuthEvent().subscribe(
            () => {
                this.checkAuth();
            }
        );
    }

    public toggleNavbar() {
        this.navbarOpened = !this.navbarOpened;
    }

    public logout() {
        this.authService.logoutUser().subscribe(
            () => {
                this.router.navigate(["/login"]);
            },
            (err) => {
                console.error(err);
            }
        );
    }

    private checkAuth(): void {
        this.authService.checkAuthorization().subscribe(
            (isAuthorized: boolean) => {
                this.isAuthorized = isAuthorized;
            }
        );
    }

}
