import { Component, Input, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';
import { SBSortableHeaderDirective, SortEvent } from '@modules/tables/directives';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';

import {FormGroup, FormControl} from '@angular/forms';
import { LeaveappService } from '../services/leaveapp.service';


@Component({
    selector: 'sb-leave-tracker',
    templateUrl: './leave-tracker.component.html',
    styleUrls: ['./leave-tracker.component.scss'],
})
export class LeaveTrackerComponent implements OnInit {
    constructor(
        public leaveappService: LeaveappService,
        private modalService: NgbModal,
        public router: Router
    ) {}
    @Input() pageSize = 4;

    // range = new FormGroup({
    //     start: new FormControl(),
    //     end: new FormControl()
    // });

    leaveapps$!: Observable<LeaveApplication[]>;
    total$!: Observable<number>;
    sortedColumn!: string;
    sortedDirection!: string;
    startdate!: any;
    enddate!:string;
    closeResult!: string;
    la!: string;

    @ViewChildren(SBSortableHeaderDirective) headers!: QueryList<SBSortableHeaderDirective>;

    leave!: LeaveApplication;
    newleave: LeaveApplication = {
        id: '0',
        employee: JSON.parse(localStorage.getItem('Auth-User')!),
        startDate: '',
        endDate:'',
        approved: 'false',
    };

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
    open(content: any, leave: LeaveApplication) {
        this.leave = leave;
        if (this.leave === undefined) {
            console.log('here in create open model');
        } else {
            console.log(leave.id);
            this.la = leave.id;
        }
        this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
            result => {
                this.closeResult = `Closed with: ${result}`;
            },
            reason => {
                this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
            }
        );
    }

    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }
    }
    updatestart(dateObject:any) {
        const stringified = JSON.stringify(dateObject.value);
        const sd = stringified.substring(1, 11);
        this.startdate = sd;
    }
    updateend(dateObject:any) {
        const stringified = JSON.stringify(dateObject.value);
        const ed = stringified.substring(1, 11);
        this.enddate = ed;
    }
    create() {
        console.log('Start date ' + this.startdate);
        console.log('End date ' + this.enddate);
        this.newleave.startDate = this.startdate;
        this.newleave.endDate = this.enddate;
        this.leaveappService.createLeave(this.newleave);
        // hack - auto refresh after 1.2 sec
        setTimeout(() => {
            this.redirectTo('/leaveapplication/track');
        }, 1200);
    }
    update() {
        console.log('id of leave to be updated is' + this.leave.id);
        // console.log('days before update ' + this.leave.days);
        console.log('Start date ' + this.startdate);
        console.log('End date ' + this.enddate);
        this.leave.startDate = this.startdate;
        this.leave.endDate = this.enddate;
        this.leaveappService.saveLeave(this.leave);
    }

    async delete() {
        console.log('id of leave to be deleted is' + this.leave.id);
        this.leaveappService.deleteLeave(this.leave.id);
        // hack - auto refresh after 1.2 sec
        setTimeout(() => {
            this.redirectTo('/leaveapplication/track');
        }, 1200);
    }

    redirectTo(uri: string) {
        this.router
            .navigateByUrl('/', { skipLocationChange: true })
            .then(() => this.router.navigate([uri]));
    }
}
