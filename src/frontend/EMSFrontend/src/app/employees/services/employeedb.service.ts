import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Employee } from '../../models/employee.model';
import { catchError, retry,map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EmployeedbService {

  http:HttpClient;
  constructor(http: HttpClient) {
    this.http = http;
  }

  public getEmployees():Observable<Employee []>{

    const httpOptions = {
      headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin' : 'http://localhost:8080',
      'Access-Control-Allow-Methods' : 'GET, POST, PUT, DELETE'
      })
    };

    let url:string = 'http://localhost:8080/employees';
    return this.http.get<Employee []>(url,httpOptions);
  }

}
