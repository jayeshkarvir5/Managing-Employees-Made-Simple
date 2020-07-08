import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeedbService } from '@app/employees/services/employeedb.service';
import { User } from '@modules/auth/models';
import { AuthService } from '@modules/auth/services';
import Chart from 'chart.js';
import { Subscription } from 'rxjs';

@Component({
    selector: 'sb-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit, OnDestroy {
    user!: User;
    subscription: Subscription = new Subscription();
    @ViewChild('myPieChart') myPieChart!: ElementRef<HTMLCanvasElement>;
    chart!: Chart;

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
                        this.buildPieCharts();
                    }
                })
            );
        } else {
            const authUser = authService.getAuthUser();
            if (authUser) {
                this.user = authUser;
                this.buildPieCharts();
            } else {
                router.navigate(['/auth/login']);
            }
        }
    }

    ngOnInit(): void {}

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

    async buildPieCharts() {
        const projectStats: any = await this.employeedbService
            .getProjectStats(this.user.id)
            .toPromise();
        this.chart = new Chart(this.myPieChart.nativeElement, {
            type: 'pie',
            data: {
                labels: projectStats.map((e: { projectName: any }) => e.projectName),
                datasets: [
                    {
                        data: projectStats.map((e: { duration: any }) => e.duration),
                        backgroundColor: projectStats.map((e: any) => this.generateRandomColor()),
                    },
                ],
            },
        });
    }

    generateRandomColor() {
        // tslint:disable-next-line:no-bitwise
        return '#' + (((1 << 24) * Math.random()) | 0).toString(16);
    }
}
