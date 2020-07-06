import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AuthService } from '@modules/auth/services';

@Component({
    selector: 'sb-login',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './login.component.html',
    styleUrls: ['login.component.scss'],
})
export class LoginComponent implements OnInit {
    loginDetails: any = {};

    constructor(private authService: AuthService) {}
    ngOnInit() {}

    login(loginDetails: any) {
        console.log(loginDetails);
        this.authService.login(loginDetails.email, loginDetails.password);
    }
}
