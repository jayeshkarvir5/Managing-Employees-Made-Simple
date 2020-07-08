import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppCommonModule } from '@common/app-common.module';
import { AuthInterceptor } from '@modules/auth/auth.interceptor';
import { AuthService } from '@modules/auth/services';
import { ChartsModule } from '@modules/charts/charts.module';
import { DashboardModule } from '@modules/dashboard/dashboard.module';
import { NavigationModule } from '@modules/navigation/navigation.module';
import { TablesModule } from '@modules/tables/tables.module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EmployeesTableComponent } from './employees/employees-table/employees-table.component';
import { EmployeesComponent } from './employees/employees.component';
import { EmployeeService } from './employees/services/employee.service';
import { EmployeedbService } from './employees/services/employeedb.service';
import { AuthGuardService } from './guards/auth-guard.service';
import { LogoutComponent } from './logout/logout.component';
import { ProfileComponent } from './profile/profile.component';
import { ProjectService } from './projects/project.service';

@NgModule({
    declarations: [AppComponent, EmployeesComponent, EmployeesTableComponent, ProfileComponent, LogoutComponent, LeaveApplicationComponent, LeaveTrackerComponent, LeaveManagerComponent],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        DashboardModule,
        CommonModule,
        RouterModule,
        ReactiveFormsModule,
        FormsModule,
        AppCommonModule,
        NavigationModule,
        ChartsModule,
        TablesModule,
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
        EmployeeService,
        EmployeedbService,
        ProjectService,
        AuthService,
        AuthGuardService,
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
