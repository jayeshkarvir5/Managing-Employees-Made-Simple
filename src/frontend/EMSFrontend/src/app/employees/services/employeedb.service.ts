import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '@modules/auth/models';
import { AuthService } from '@modules/auth/services';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class EmployeedbService {
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': 'http://localhost:8080',
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
        }),
    };
    url = 'http://localhost:8080/employees';

    constructor(private http: HttpClient) {
        this.http = http;
    }

    public getAllEmployees(): Observable<User[]> {
        return this.http.get<User[]>(this.url, this.httpOptions);
    }

    public getEmployee(employeeId: string): Observable<User> {
        return this.http.get<User>(this.url + '/' + employeeId, this.httpOptions);
    }

    public saveEmployee(employeeData: any) {
        this.http
            .post(this.url, employeeData, this.httpOptions)
            .subscribe(e => console.log('save employee result -> ', e));
    }

    public updateEmployee(employeeData: User) {
        this.http.put(this.url, employeeData, this.httpOptions);
    }

    public deleteEmployee(employeeId: string) {
        this.http.delete(this.url + '/' + employeeId, this.httpOptions);
    }

    public getEmployeeHeirarcy(employeeId: string) {
        return this.http.get(this.url + '/' + employeeId + '/heirarchy', this.httpOptions);
    }

    // If i am a manager and i want the leave requests of employees under me
    public leaveRequests(managerId: string) {
        return this.http.get(this.url + managerId, this.httpOptions);
    }
}
