import { Component, OnInit } from '@angular/core';
import { UserService } from '@modules/auth/services';

@Component({
    selector: 'sb-employees',
    templateUrl: './employees.component.html',
    styleUrls: ['./employees.component.scss'],
})
export class EmployeesComponent implements OnInit {
    constructor(private userService: UserService) {
        userService.user$.subscribe(user => {
            console.log('user-->', user);
        });
    }

    ngOnInit(): void {}
}
