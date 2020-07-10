import { Component, Input, OnInit, QueryList, ViewChildren } from '@angular/core';
import { SBSortableHeaderDirective, SortEvent } from '@modules/tables/directives';
import { Observable } from 'rxjs';

import { LeaveappService } from '../services/leaveapp.service';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';
import { NgbModal,ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

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
  days!:string;
  closeResult!:string;
  la!:string;

  @ViewChildren(SBSortableHeaderDirective) headers!: QueryList<SBSortableHeaderDirective>;

  constructor(public leaveappService: LeaveappService,
                    private modalService:NgbModal) {}

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

  leave!:LeaveApplication;
  newleave: LeaveApplication ={
    id:'0',
    employee:JSON.parse(localStorage.getItem('Auth-User')!),
    days:'0',
    approved:'false'
  };
  open(content:any,leave:LeaveApplication) {
    this.leave = leave;
    if(this.leave==undefined){
      console.log("here in create open model");
    }else{
      console.log(leave.id);
      this.la = leave.id;
    }
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
  create(){
    console.log("days for leave"+ this.days);
    this.newleave.days = this.days;
    this.leaveappService.createLeave(this.newleave);
  }
  update(){
    console.log("id of leave to be updated is" + this.leave.id);
    console.log("days before update "+ this.leave.days);
    console.log("days after update" + this.days);
    this.leave.days = this.days;
    this.leaveappService.saveLeave(this.leave);
    
  }
  delete(){
    console.log("id of leave to be deleted is" + this.leave.id);
    this.leaveappService.deleteLeave(this.leave.id);
  }
}
