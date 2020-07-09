import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeedbService } from '@app/employees/services/employeedb.service';
import { User } from '@modules/auth/models';
import { AuthService } from '@modules/auth/services';
import { Subscription, Observable } from 'rxjs';
//import { stringify } from 'querystring';
import { NgbModal,ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'sb-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit, OnDestroy {
    user!: User;
    authUserId!: string;
    userId!: string;
    password:any = {
        oldPassword:String,
        newPassword:String
    }
    subscription: Subscription = new Subscription();
    constructor(
        public route: ActivatedRoute,
        public authService: AuthService,
        public employeedbService: EmployeedbService,
        public router: Router,
        private modalService:NgbModal
    ) {

        this.password["oldPassword"]="";
        this.password["newPassword"]="";

        const paramId = this.route.snapshot.paramMap.get('id');

        const authUserId = authService.getAuthUser();
        if(authUserId){
            this.authUserId = authUserId;
        }
        else{
            router.navigate(['/auth/login']);
        }

        if(paramId){
            this.userId = paramId;
        }
        else{
            this.userId = this.authUserId;
        }

        this.subscription.add(
            this.employeedbService.getEmployee(this.userId).subscribe(user=>{
                if(user){
                    this.user = user;
                    console.log(this.user);
                }
            })
        );
    }

    save(){
        this.employeedbService.updateEmployee(this.user);
    }

    resetPassword(){
        console.log("resetPassword()called");
        console.log(this.password["oldPassword"]);
        console.log(this.password["newPassword"]);

        this.employeedbService.resetPassword(this.user.id,this.password["oldPassword"],this.password["newPassword"])
    }

    myProfile():boolean{
        if(this.authUserId==this.user.id){
            return true;
        }
        return false;
    }

    ngOnInit(): void {}

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }


    closeResult!: string;

    open(content:any) {
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
}
