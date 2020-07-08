import { Injectable, OnInit, PipeTransform } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { SortDirection } from '@modules/tables/directives';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { debounceTime, delay, switchMap, tap } from 'rxjs/operators';
import { LeaveappdbService } from './leaveappdb.service';


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

function sort(leaveapps: LeaveApplication[], column: string, direction: string): LeaveApplication[] {
  if (direction === '') {
      return leaveapps;
  } else {
      return [...leaveapps].sort((a: any, b: any) => {
          const res = compare(a[column], b[column]);
          return direction === 'asc' ? res : -res;
      });
  }
}

function matches(leaveapp: LeaveApplication, term: string, pipe: PipeTransform) {
  return (
      leaveapp.employee.id.toLowerCase().includes(term.toLowerCase()) ||
      leaveapp.employee.firstName.toLowerCase().includes(term.toLowerCase()) ||
      leaveapp.employee.lastName.toLowerCase().includes(term.toLowerCase())
  );
}
@Injectable({
  providedIn: 'root'
})
export class LeaveappService {
  private _loading$ = new BehaviorSubject<boolean>(true);
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

  constructor(private pipe: DecimalPipe, private leaveappdbService: LeaveappdbService) {
      this.leaveappdbService.getAll().subscribe(e => {
          this._leaveapps$.next(e);
          this._total$.next(e.length);
          this.leaveappList = e;
          console.log(e);
      });
    }

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

  private _set(patch: Partial<State>) {
      Object.assign(this._state, patch);
      // this._search$.next();
  }
   
}
