import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmployeeService } from '@app/employees/services/employee.service';
import { User } from '@modules/auth/models';
import { UserService } from '@modules/auth/services';
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
        public userService: UserService,
        public employeeService: EmployeeService
    ) {
        const userId = this.route.snapshot.paramMap.get('id');

        if (userId) {
            this.subscription.add(
                employeeService.getEmployeeFromId(userId).subscribe(user => {
                    if (user) {
                        this.user = user;
                    }
                })
            );
        } else {
            this.subscription.add(userService.user$.subscribe(user => (this.user = user)));
        }
    }

    ngOnInit(): void {}

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
