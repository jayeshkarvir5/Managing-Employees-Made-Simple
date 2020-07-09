import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '@app/employees/services/employee.service';
import { EmployeedbService } from '@app/employees/services/employeedb.service';
import { ProjectService } from '@app/projects/project.service';
import { User } from '@modules/auth/models';
import { Project } from '@modules/auth/models/project.model';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { Router } from '@angular/router';

@Component({
    selector: 'sb-new-user',
    templateUrl: './new-user.component.html',
    styleUrls: ['./new-user.component.scss'],
})
export class NewUserComponent implements OnInit {
    projectDropdownList: any[] = [];
    projectSelectedItems: any[] = [];
    projectDropdownSettings: IDropdownSettings = {};

    techStackDropdownList: any[] = [];
    techStackSelectedItems: any[] = [];
    techStackDropdownSettings: IDropdownSettings = {};

    user: any = {};

    designations = [
        'CEO',
        'MD',
        'ED',
        'VP',
        'Admin',
        'Senior Manager',
        'Manager',
        'Senior Associate',
        'Associate',
    ];

    constructor(
        private projectService: ProjectService,
        private employeeService: EmployeedbService,
        private router: Router
    ) {}

    async ngOnInit() {
        const projects: Project[] | undefined = await this.projectService.getAllProjects();

        if (projects) {
            this.projectDropdownList = projects.map(project => {
                return {
                    project_id: project.id,
                    project_text: project.name,
                };
            });
        }
        this.projectSelectedItems = [];
        this.projectDropdownSettings = {
            singleSelection: false,
            idField: 'project_id',
            textField: 'project_text',
            selectAllText: 'Select All',
            unSelectAllText: 'UnSelect All',
            allowSearchFilter: true,
        };

        this.techStackDropdownList = [
            { id: 'Java', name: 'Java' },
            { id: 'C++', name: 'C++' },
            { id: 'Spring Boot', name: 'Spring Boot' },
            { id: 'Hibernate', name: 'Hibernate' },
            { id: 'SQL', name: 'SQL' },
            { id: 'Angular', name: 'Angular' },
            { id: 'React', name: 'React' },
        ];
        this.techStackSelectedItems = [];
        this.techStackDropdownSettings = {
            singleSelection: false,
            idField: 'id',
            textField: 'name',
            selectAllText: 'Select All',
            unSelectAllText: 'UnSelect All',
            allowSearchFilter: true,
        };
    }

    save(formUser: any) {
        console.log('Form saved', formUser);

        const newUser = {
            id: '',
            firstName: formUser.firstName,
            lastName: formUser.lastName,
            email: formUser.email,
            phoneNo: formUser.phoneNo,
            password: formUser.password,
            designation: formUser.designation,
            address: formUser.address,
            experience: formUser.experience,
            leaveApp: false,
            projects: formUser.projects.map((e: { project_id: any }) => {
                return { id: e.project_id };
            }),
            techstack: formUser.techStack.map((e: { name: any }) => e.name).join(','),
        };

        console.log(newUser);

        this.employeeService.saveEmployee(newUser);

        this.router.navigate(['/']);
    }
}
