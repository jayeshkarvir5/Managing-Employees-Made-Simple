import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LeaveApplication } from '@modules/auth/models/leaveApplication.model';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
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
    public getById(id: string): Observable<LeaveApplication[]> {
        return this.http.get<LeaveApplication[]>(this.url + '/' + id, this.httpOptions);
    }
    public createLeave(leaveapp: LeaveApplication) {
        return this.http.post(this.url, leaveapp, this.httpOptions).subscribe(la => {
            console.log('createLeave returned ' + la);
        });
    }
    public saveLeave(leaveapp: LeaveApplication) {
        return this.http.put(this.url, leaveapp, this.httpOptions).subscribe(la => {
            console.log('saveLeave returned ' + la);
        });
    }
    public empLeave(id: string): Observable<LeaveApplication[]> {
        return this.http.get<LeaveApplication[]>(this.url + '/' + id + '/leave', this.httpOptions);
    }
    public getLeaveById(id: string): Observable<LeaveApplication> {
        return this.http.get<LeaveApplication>(this.url + 'byid/' + id, this.httpOptions);
    }
    public delete(id: string) {
        console.log('in leavedb service ' + this.url + '/' + id);
        this.http.delete(this.url + '/' + id, this.httpOptions).subscribe(data => {
            // console.log("data returned by delete is "+data);
        });
    }
}
