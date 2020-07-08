import { Component, Input, OnInit, QueryList, ViewChildren } from '@angular/core';
import { SBSortableHeaderDirective, SortEvent } from '@modules/tables/directives';
import { Observable } from 'rxjs';

import { LeaveappService } from '../services/leaveapp.service';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';

@Component({
  selector: 'sb-leave-manager',
  templateUrl: './leave-manager.component.html',
  styleUrls: ['./leave-manager.component.scss']
})
export class LeaveManagerComponent implements OnInit {
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
      this.leaveappService.getAll();
      // console.log(localStorage.getItem('Auth-User'));
  }

  onSort({ column, direction }: SortEvent) {
      this.sortedColumn = column;
      this.sortedDirection = direction;
      this.leaveappService.sortColumn = column;
      this.leaveappService.sortDirection = direction;
  }

}
