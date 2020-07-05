import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { USERS } from '@app/data/employee.data';
import { User } from '@modules/auth/models';
import { catchError, retry,map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EmployeedbService {

  http:HttpClient;
  constructor(http: HttpClient) {
    this.http = http;
  }

  public getEmployees():Observable<User []>{

    const httpOptions = {
      headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin' : 'http://localhost:8080',
      'Access-Control-Allow-Methods' : 'GET, POST, PUT, DELETE'
      })
    };

    let url:string = 'http://localhost:8080/employees';
    return this.http.get<User []>(url,httpOptions);
  }

}
