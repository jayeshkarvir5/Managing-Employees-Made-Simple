import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { EmployeesComponent } from './employees/employees.component';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: '/dashboard',
    },
    {
        path: 'employees',
        component: EmployeesComponent,
    },
    {
        path: 'dashboard',
        component: ProfileComponent,
    },
    {
        path: 'profile/:id',
        component: ProfileComponent,
    },
    {
        path: '**',
        pathMatch: 'full',
        loadChildren: () =>
            import('modules/error/error-routing.module').then(m => m.ErrorRoutingModule),
    },
    // {
    //     path: '',
    //     pathMatch: 'full',
    //     redirectTo: '/dashboard',
    // },
    // {
    //     path: 'charts',
    //     loadChildren: () =>
    //         import('modules/charts/charts-routing.module').then(m => m.ChartsRoutingModule),
    // },
    // {
    //     path: 'dashboard',
    //     loadChildren: () =>
    //         import('modules/dashboard/dashboard-routing.module').then(
    //             m => m.DashboardRoutingModule
    //         ),
    // },
    // {
    //     path: 'profile',
    //     component: ProfileComponent,
    // },
    // {
    //     path: 'auth',
    //     loadChildren: () =>
    //         import('modules/auth/auth-routing.module').then(m => m.AuthRoutingModule),
    // },
    // {
    //     path: 'error',
    //     loadChildren: () =>
    //         import('modules/error/error-routing.module').then(m => m.ErrorRoutingModule),
    // },
    // {
    //     path: 'tables',
    //     loadChildren: () =>
    //         import('modules/tables/tables-routing.module').then(m => m.TablesRoutingModule),
    // },
    // {
    //     path: 'version',
    //     loadChildren: () =>
    //         import('modules/utility/utility-routing.module').then(m => m.UtilityRoutingModule),
    // },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
