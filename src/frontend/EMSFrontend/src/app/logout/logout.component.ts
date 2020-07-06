import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@modules/auth/services';

@Component({
    // tslint:disable-next-line:component-selector
    selector: '',
    template: '',
})
export class LogoutComponent implements OnInit {
    constructor(private authService: AuthService, private router: Router) {}

    ngOnInit(): void {
        this.authService.logout();
        this.router.navigate(['/auth/login']);
    }
}
