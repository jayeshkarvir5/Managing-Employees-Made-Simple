import { Component, Input, OnInit, QueryList, ViewChildren } from '@angular/core';
import { SBSortableHeaderDirective, SortEvent } from '@modules/tables/directives';
import { Subscription, Observable } from 'rxjs';

import { LeaveappService } from '../services/leaveapp.service';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';
import { NgbModal,ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

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
  closeResult!:string;
  la!:string;
  subscription: Subscription = new Subscription();

  @ViewChildren(SBSortableHeaderDirective) headers!: QueryList<SBSortableHeaderDirective>;

  constructor(public leaveappService: LeaveappService,
                    private modalService:NgbModal) {}

  ngOnInit() {
      this.leaveappService.pageSize = this.pageSize;
      this.leaveapps$ = this.leaveappService.leaveapps$;
      this.total$ = this.leaveappService.total$;
      this.subscription.add(this.leaveappService.getEmpLeave());
      // console.log(localStorage.getItem('Auth-User'));
  }

  onSort({ column, direction }: SortEvent) {
      this.sortedColumn = column;
      this.sortedDirection = direction;
      this.leaveappService.sortColumn = column;
      this.leaveappService.sortDirection = direction;
  }
  leave!:LeaveApplication;
  open(content:any,leave:LeaveApplication) {
    this.leave = leave;
    console.log(leave.id);
    this.la = leave.id;
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
}

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  approve(){
    console.log("here "+this.la);
    // let leave = this.leaveappService.getLeaveById(this.la);
    console.log("in component "+this.leave.approved);
    if(this.leave.approved === 'false'){
      console.log("inside if");
      this.leave.approved = 'true';
    }else{
      this.leave.approved = 'false';
    }
   
    console.log("in component "+this.leave.approved);
    this.leaveappService.saveLeave(this.leave);
  }
}
