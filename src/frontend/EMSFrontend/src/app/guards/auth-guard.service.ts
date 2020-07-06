import { Injectable } from '@angular/core';
import { CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '@modules/auth/services';

@Injectable({
    providedIn: 'root',
})
export class AuthGuardService implements CanActivate {
    constructor(private authService: AuthService, private router: Router) {}

    canActivate(route: any, state: RouterStateSnapshot) {
        const appUser = this.authService.getAuthUser();

        if (!appUser) {
            this.router.navigate(['/auth/login'], { queryParams: { returnUrl: state.url } });
            return false;
        }
        return true;
    }
}
