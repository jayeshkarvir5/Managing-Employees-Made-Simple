import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';
import { LeaveappService } from '../services/leaveapp.service';

@Component({
  selector: 'sb-leave-application',
  templateUrl: './leave-application.component.html',
  styleUrls: ['./leave-application.component.scss']
})
export class LeaveApplicationComponent implements OnInit {

  constructor() {
    
   }

  ngOnInit(): void {
  }

}
