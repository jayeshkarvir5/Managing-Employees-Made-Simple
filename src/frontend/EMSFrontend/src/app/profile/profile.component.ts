import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeedbService } from '@app/employees/services/employeedb.service';
import { User } from '@modules/auth/models';
import { AuthService } from '@modules/auth/services';
import { Subscription } from 'rxjs';

@Component({
    selector: 'sb-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit, OnDestroy {
    user!: User;
    subscription: Subscription = new Subscription();
    constructor(
        public route: ActivatedRoute,
        public authService: AuthService,
        public employeedbService: EmployeedbService,
        public router: Router
    ) {
        const userId = this.route.snapshot.paramMap.get('id');

        if (userId) {
            this.subscription.add(
                employeedbService.getEmployee(userId).subscribe(user => {
                    if (user) {
                        this.user = user;
                    }
                })
            );
        } else {
            const authUser = authService.getAuthUser();
            if (authUser) {
                this.user = authUser;
            } else {
                router.navigate(['/auth/login']);
            }
        }
    }

    ngOnInit(): void {}

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
