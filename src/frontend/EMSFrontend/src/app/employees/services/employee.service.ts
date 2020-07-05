import { DecimalPipe } from '@angular/common';
import { Injectable, PipeTransform } from '@angular/core';
import { USERS } from '@app/data/employee.data';
import { User } from '@modules/auth/models';
import { SortDirection } from '@modules/tables/directives';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { debounceTime, delay, switchMap, tap } from 'rxjs/operators';
import { EmployeedbService } from './employeedb.service';
import { catchError, retry,map } from 'rxjs/operators';

interface SearchResult {
    Users:User[];
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

function sort(users: User[], column: string, direction: string): User[] {
    if (direction === '') {
        return users;
    } else {
        return [...users].sort((a: any, b: any) => {
            const res = compare(a[column], b[column]);
            return direction === 'asc' ? res : -res;
        });
    }
}

function matches(user: User, term: string, pipe: PipeTransform) {
    return (
        user.firstName.toLowerCase().includes(term.toLowerCase()) ||
        user.lastName.toLowerCase().includes(term.toLowerCase()) ||
        user.email.toLowerCase().includes(term.toLowerCase())
    );
}

@Injectable({ providedIn: 'root' })
export class EmployeeService {
    private _loading$ = new BehaviorSubject<boolean>(true);
    private _search$ = new Subject<void>();
    private _employees$ = new BehaviorSubject<User[]>([]);
    private _total$ = new BehaviorSubject<number>(0);

    private employeesList:User[] = [];

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
                this._employees$.next(result.Users);
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

        // 1. sort
        let Users = sort(this.employeesList, sortColumn, sortDirection);

        // 2. filter
        Users = Users.filter(country => matches(country, searchTerm, this.pipe));
        const total = Users.length;

        // 3. paginate
        Users = Users.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
        return of({ Users, total });
    }

    public getEmployeeFromId(id: string): Observable<User | undefined> {
        const user = USERS.find(o => o.id === id);
        return user ? of(user) : of(undefined);
    }
}
