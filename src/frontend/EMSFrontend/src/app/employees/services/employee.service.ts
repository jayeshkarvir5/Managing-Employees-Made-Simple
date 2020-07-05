import { DecimalPipe } from '@angular/common';
import { Injectable, PipeTransform } from '@angular/core';
import { Employee } from '@app/models/employee.model';
import { SortDirection } from '@modules/tables/directives';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { debounceTime, delay, switchMap, tap } from 'rxjs/operators';
import { EmployeedbService } from './employeedb.service';
import { EMPLOYEES } from '../../data/employee.data';
import { catchError, retry,map } from 'rxjs/operators';

interface SearchResult {
    employees: Employee[];
    total: number;
}

interface State {
    page: number;
    pageSize: number;
    searchTerm: string;
    sortColumn: string;
    sortDirection: SortDirection;
}

function compare(v1: number | string, v2: number | string) {
    return v1 < v2 ? -1 : v1 > v2 ? 1 : 0;
}

function sort(employees: Employee[], column: string, direction: string): Employee[] {
    if (direction === '') {
        return employees;
    } else {
        return [...employees].sort((a, b) => {
            const res = compare(a[column], b[column]);
            return direction === 'asc' ? res : -res;
        });
    }
}

function matches(employee: Employee, term: string, pipe: PipeTransform) {
    console.log(employee);
    return (
        employee.firstName.toLowerCase().includes(term.toLowerCase()) ||
        employee.lastName.toLowerCase().includes(term.toLowerCase()) ||
        employee.email.toLowerCase().includes(term.toLowerCase())
    );
}

@Injectable({ providedIn: 'root' })
export class EmployeeService {
    private _loading$ = new BehaviorSubject<boolean>(true);
    private _search$ = new Subject<void>();
    private _employees$ = new BehaviorSubject<Employee[]>([]);
    private _total$ = new BehaviorSubject<number>(0);

    private employeesList:Employee[] = [];

    private _state: State = {
        page: 1,
        pageSize: 4,
        searchTerm: '',
        sortColumn: '',
        sortDirection: '',
    };

    constructor(private pipe: DecimalPipe,private employeedbService:EmployeedbService) {
        
        this.loadData();

        this._search$
            .pipe(
                tap(() => this._loading$.next(true)),
                debounceTime(120),
                switchMap(() => this._search()),
                delay(120),
                tap(() => this._loading$.next(false))
            )
            .subscribe(result => {
                this._employees$.next(result.employees);
                this._total$.next(result.total);
            });

        this._search$.next();
    }

    get employees$() {
        return this._employees$.asObservable();
    }
    get total$() {
        return this._total$.asObservable();
    }
    get loading$() {
        return this._loading$.asObservable();
    }
    get page() {
        return this._state.page;
    }
    set page(page: number) {
        this._set({ page });
    }
    get pageSize() {
        return this._state.pageSize;
    }
    set pageSize(pageSize: number) {
        this._set({ pageSize });
    }
    get searchTerm() {
        return this._state.searchTerm;
    }
    set searchTerm(searchTerm: string) {
        this._set({ searchTerm });
    }
    set sortColumn(sortColumn: string) {
        this._set({ sortColumn });
    }
    set sortDirection(sortDirection: SortDirection) {
        this._set({ sortDirection });
    }

    private loadData(){
        this.employeedbService.getEmployees().pipe(map((x=>{
            this.employeesList = x;
        }))).subscribe();
    }

    private _set(patch: Partial<State>) {
        Object.assign(this._state, patch);
        this._search$.next();
    }

    private _search(): Observable<SearchResult> {
        const { sortColumn, sortDirection, pageSize, page, searchTerm } = this._state;
        console.log("--------------------- page loaded");
        console.log(this.employeedbService.getEmployees())
        // 1. sort
        console.log(this.employeesList);
        
        let employees = sort(this.employeesList, sortColumn, sortDirection);

        // 2. filter
        employees = employees.filter(country => matches(country, searchTerm, this.pipe));
        const total = employees.length;

        // 3. paginate
        employees = employees.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
        return of({ employees, total });
    }

    private __search():Observable<SearchResult> {
        const { sortColumn, sortDirection, pageSize, page, searchTerm } = this._state;

        // 1. sort
        console.log(this.employeedbService.getEmployees());
        let employees = sort(EMPLOYEES, sortColumn, sortDirection);

        // 2. filter
        employees = employees.filter(country => matches(country, searchTerm, this.pipe));
        const total = employees.length;

        // 3. paginate
        employees = employees.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
        return of({ employees, total });
    }
}
