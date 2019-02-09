import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user.class";

@Component({
    selector: "tasker-user-profile-page",
    templateUrl: "./user-profile-page.component.html",
    styleUrls: ["./user-profile-page.component.scss"]
})
export class UserProfilePageComponent implements OnInit {

    public loginInfo: User;
    public pass: {
        pass1: string,
        pass2: string
    };
    public states: {
        notSame: boolean,
        error: boolean,
        success: boolean
    };

    constructor(private authService: AuthService) {
    }

    ngOnInit() {
        this.pass = {
            pass1: "",
            pass2: ""
        };
        this.states = {
            notSame: false,
            error: false,
            success: false
        };
        this.loginInfo = new User();
        this.getCurrentUser();
    }

    public checkPasswordMatch() {
        if (this.pass.pass2 !== "" && this.pass.pass2 !== this.pass.pass1) {
            this.states.notSame = true;
        } else {
            this.states.notSame = false;
        }
    }

    public changePassword($event: Event) {
        $event.preventDefault();

        if (this.pass.pass1 !== this.pass.pass2) {
            return;
        }
        this.states = {
            notSame: false,
            error: false,
            success: false
        };

        this.loginInfo.password = this.pass.pass1;
        this.authService.changePassword(this.loginInfo).subscribe(
            () => {
                this.states = {
                    notSame: false,
                    error: false,
                    success: true
                };
            },
            (err) => {
                console.error(err);
                this.states = {
                    notSame: false,
                    error: true,
                    success: false
                };
            }
        );
    }

    private getCurrentUser() {
        this.authService.getCurrentUser().subscribe(
            (user: User) => {
                this.loginInfo.id = user.id;
                this.loginInfo.username = user.username;
            },
            (err) => console.error(err)
        );
    }

}
