import { Component, Input, OnInit, QueryList, ViewChildren } from '@angular/core';
import { SBSortableHeaderDirective, SortEvent } from '@modules/tables/directives';
import { Observable } from 'rxjs';

import { LeaveappService } from '../services/leaveapp.service';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';

@Component({
  selector: 'sb-leave-tracker',
  templateUrl: './leave-tracker.component.html',
  styleUrls: ['./leave-tracker.component.scss']
})
export class LeaveTrackerComponent implements OnInit {
  @Input() pageSize = 4;

  leaveapps$!: Observable<LeaveApplication[]>;
  total$!: Observable<number>;
  sortedColumn!: string;
  sortedDirection!: string;

  @ViewChildren(SBSortableHeaderDirective) headers!: QueryList<SBSortableHeaderDirective>;

  constructor(public leaveappService: LeaveappService) {}

  ngOnInit() {
      this.leaveappService.pageSize = this.pageSize;
      this.leaveapps$ = this.leaveappService.leaveapps$;
      this.total$ = this.leaveappService.total$;
      this.leaveappService.getById();
      // console.log(localStorage.getItem('Auth-User'));
  }

  onSort({ column, direction }: SortEvent) {
      this.sortedColumn = column;
      this.sortedDirection = direction;
      this.leaveappService.sortColumn = column;
      this.leaveappService.sortDirection = direction;
  }

}
