import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';

@Injectable({
  providedIn: 'root'
})
export class LeaveappdbService {
  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:8080',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
    }),
};

  url = 'http://localhost:8080/leaveapplications';
   constructor(private http: HttpClient) {
    this.http = http;
   }
  public getAll(): Observable<LeaveApplication[]> {
    return this.http.get<LeaveApplication[]>(this.url, this.httpOptions);
}
}
