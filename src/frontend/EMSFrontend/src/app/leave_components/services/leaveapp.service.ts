import { DecimalPipe } from '@angular/common';
import { Injectable, OnInit, PipeTransform } from '@angular/core';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';
import { SortDirection } from '@modules/tables/directives';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { debounceTime, delay, switchMap, tap } from 'rxjs/operators';

import { LeaveappdbService } from './leaveappdb.service';

interface SearchResult {
    Leaves: LeaveApplication[];
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

function sort(
    leaveapps: LeaveApplication[],
    column: string,
    direction: string
): LeaveApplication[] {
    if (direction === '') {
        return leaveapps;
    } else {
        return [...leaveapps].sort((a: any, b: any) => {
            if (column === 'firstName' || column === 'lastName') {
                const res = compare(a['employee'][column], b['employee'][column]);
                return direction === 'asc' ? res : -res;
            }
            const res = compare(a[column], b[column]);
            return direction === 'asc' ? res : -res;
        });
    }
}

function matches(leaveapp: LeaveApplication, term: string, pipe: PipeTransform) {
    return (
        leaveapp.employee.firstName.toLowerCase().includes(term.toLowerCase()) ||
        leaveapp.employee.lastName.toLowerCase().includes(term.toLowerCase())
    );
}
@Injectable({
    providedIn: 'root',
})
export class LeaveappService {
    constructor(private pipe: DecimalPipe, private leaveappdbService: LeaveappdbService) {}
    get leaveapps$() {
        return this._leaveapps$.asObservable();
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
    private _loading$ = new BehaviorSubject<boolean>(true);
    private _search$ = new Subject<void>();
    private _leaveapps$ = new BehaviorSubject<LeaveApplication[]>([]);
    private _total$ = new BehaviorSubject<number>(0);

    private leaveappList: LeaveApplication[] = [];

    private _state: State = {
        page: 1,
        pageSize: 4,
        searchTerm: '',
        sortColumn: '',
        sortDirection: '',
    };

    leave!: LeaveApplication;

    getById() {
        let id = localStorage.getItem('Auth-User-Id');
        id = id == null ? '999' : id;

        this.leaveappdbService.getById(id).subscribe(e => {
            this._leaveapps$.next(e);
            this._total$.next(e.length);
            this.leaveappList = e;
            this._search$.next();
            console.log(e);
        });
        this._search$
            .pipe(
                tap(() => this._loading$.next(true)),
                debounceTime(120),
                switchMap(() => this._search()),
                delay(120),
                tap(() => this._loading$.next(false))
            )
            .subscribe(result => {
                this._leaveapps$.next(result.Leaves);
                this._total$.next(result.total);
            });
    }

    getEmpLeave() {
        let id = localStorage.getItem('Auth-User-Id');
        id = id === null ? '999' : id;
        this.leaveappdbService.empLeave(id).subscribe(e => {
            this._leaveapps$.next(e);
            this._total$.next(e.length);
            this.leaveappList = e;
            this._search$.next();
            console.log(e);
        });
        this._search$
            .pipe(
                tap(() => this._loading$.next(true)),
                debounceTime(120),
                switchMap(() => this._search()),
                delay(120),
                tap(() => this._loading$.next(false))
            )
            .subscribe(result => {
                this._leaveapps$.next(result.Leaves);
                this._total$.next(result.total);
            });
    }
    getLeaveById(id: string) {
        this.leaveappdbService.getLeaveById(id).subscribe(la => {
            if (la) {
                this.leave = la;
                console.log('in service ' + this.leave.startDate);
            }
        });
        console.log('in service outside subscribe' + this.leave.startDate);
        return this.leave;
    }
    createLeave(leave: LeaveApplication) {
        this.leaveappdbService.createLeave(leave);
    }
    saveLeave(leave: LeaveApplication) {
        this.leaveappdbService.saveLeave(leave);
    }
    deleteLeave(id: string) {
        this.leaveappdbService.delete(id);
    }
    private _search(): Observable<SearchResult> {
        const { sortColumn, sortDirection, pageSize, page, searchTerm } = this._state;

        // 1. sort
        let Leaves = sort(this.leaveappList, sortColumn, sortDirection);

        // 2. filter
        Leaves = Leaves.filter(col => matches(col, searchTerm, this.pipe));
        const total = Leaves.length;

        // 3. paginate
        Leaves = Leaves.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
        return of({ Leaves, total });
    }
    private _set(patch: Partial<State>) {
        Object.assign(this._state, patch);
        this._search$.next();
    }
}
