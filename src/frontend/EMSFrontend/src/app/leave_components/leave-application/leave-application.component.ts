import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';
import { LeaveappdbService } from '../services/leaveappdb.service';
import { EmployeedbService } from '@app/employees/services/employeedb.service';
import { User } from '@modules/auth/models';

@Component({
  selector: 'sb-leave-application',
  templateUrl: './leave-application.component.html',
  styleUrls: ['./leave-application.component.scss']
})
export class LeaveApplicationComponent implements OnInit {
  @Input() days = '0';
  constructor(public leaveappService: LeaveappdbService, 
                    public leaveapp: LeaveApplication) { }

  ngOnInit(): void {

  }
  
  submit(){
    this.leaveapp.id = '1';
    this.leaveapp.employee =JSON.parse(localStorage.getItem('Auth-User')!);
    this.leaveapp.approved = 'false';
    this.leaveapp.days  = this.days;
    this.leaveappService.saveLeave(this.leaveapp);
  }
}
