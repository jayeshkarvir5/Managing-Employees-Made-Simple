import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    Input,
    OnInit,
    QueryList,
    ViewChildren,
} from '@angular/core';
import { Employee } from '@app/models/employee.model';
import { SBSortableHeaderDirective, SortEvent } from '@modules/tables/directives';
import { Observable } from 'rxjs';

import { EmployeeService } from '../services/employee.service';

@Component({
    selector: 'app-employees-table',
    templateUrl: './employees-table.component.html',
    styleUrls: ['employees-table.component.scss'],
})
export class EmployeesTableComponent implements OnInit {
    @Input() pageSize = 4;

    employees$!: Observable<Employee[]>;
    total$!: Observable<number>;
    sortedColumn!: string;
    sortedDirection!: string;

    @ViewChildren(SBSortableHeaderDirective) headers!: QueryList<SBSortableHeaderDirective>;

    constructor(public employeeService: EmployeeService) {}

    ngOnInit() {
        this.employeeService.pageSize = this.pageSize;
        this.employees$ = this.employeeService.employees$;
        this.total$ = this.employeeService.total$;
    }

    onSort({ column, direction }: SortEvent) {
        this.sortedColumn = column;
        this.sortedDirection = direction;
        this.employeeService.sortColumn = column;
        this.employeeService.sortDirection = direction;
    }
}
