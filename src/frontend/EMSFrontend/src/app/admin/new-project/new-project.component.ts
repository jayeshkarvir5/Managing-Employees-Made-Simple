import { Component, OnInit } from '@angular/core';
import { EmployeedbService } from '@app/employees/services/employeedb.service';
import { ProjectService } from '@app/projects/project.service';
import { User } from '@modules/auth/models';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { Router } from '@angular/router';

@Component({
    selector: 'sb-new-project',
    templateUrl: './new-project.component.html',
    styleUrls: ['./new-project.component.scss'],
})
export class NewProjectComponent implements OnInit {
    project: any = {};

    userDropdownList: any[] = [];
    userSelectedItems: any[] = [];
    userDropdownSettings: IDropdownSettings = {};

    constructor(
        private projectService: ProjectService,
        private employeeService: EmployeedbService,
        private router: Router
    ) {}

    async ngOnInit() {
        const users: User[] = await this.employeeService.getAllEmployees().toPromise();

        this.userDropdownList = users.map(employee => {
            return {
                id: employee.id,
                name: `${employee.firstName} ${employee.lastName}`,
            };
        });

        this.userDropdownSettings = {
            singleSelection: false,
            idField: 'id',
            textField: 'name',
            selectAllText: 'Select All',
            unSelectAllText: 'UnSelect All',
            allowSearchFilter: true,
        };
    }

    save(formProject: any) {
        console.log('Form project', formProject);

        const newProject: any = {
            name: formProject.name,
            description: formProject.description,
            client_name: formProject.clientName,
            duration: formProject.duration,
            employees: formProject.projects.map((e: { id: any }) => {
                return { id: e.id };
            }),
        };

        console.log(newProject);

        this.projectService.saveProject(newProject);

        this.router.navigate(['/']);
    }
}
