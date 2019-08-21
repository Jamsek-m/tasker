import {Component, OnInit} from "@angular/core";
import {UserDTO} from "../../models/user.dto";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: "tasker-login-page",
    templateUrl: "./login-page.component.html",
    styleUrls: ["./login-page.component.scss"]
})
export class LoginPageComponent implements OnInit {

    public loginInfo: UserDTO;
    public error = false;
    public returnUrl: string;

    constructor(private authService: AuthService, private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.loginInfo = new UserDTO();
        this.route.queryParams.subscribe(params => {
            this.returnUrl = params["return"] || "/";
        });
    }

    public login($event: Event) {
        $event.preventDefault();
        this.error = false;
        /*this.authService.loginUser(this.loginInfo).subscribe(
            () => {
                this.router.navigateByUrl(this.returnUrl)
            },
            (err) => {
                console.error(err);
                this.error = true;
            }
        );*/
    }

}
