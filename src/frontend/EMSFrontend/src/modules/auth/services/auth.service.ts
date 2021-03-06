import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { User } from '../models';

enum CONSTANTS {
    AUTH_URL = 'http://localhost:8080/authenticate',
    ACCESS_TOKEN = 'Access-Token',
    AUTH_USER = 'Auth-User',
    AUTH_ID = 'Auth-User-Id'
}

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    constructor(private http: HttpClient, private router: Router) {}

    getAuth$(): Observable<{}> {
        return of({});
    }

    login(email: string, password: string) {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Methods': 'POST',
            }),
        };
        const data = {
            username: email,
            password,
        };
        this.http.post(CONSTANTS.AUTH_URL, data, httpOptions).subscribe((response: any) => {
            console.log('Auth Response', response);
            if (response.token && response.employee) {
                localStorage.setItem(CONSTANTS.ACCESS_TOKEN, response.token);
                localStorage.setItem(CONSTANTS.AUTH_USER, JSON.stringify(response.employee));
                localStorage.setItem(CONSTANTS.AUTH_ID, JSON.stringify(response.employee.id));
                this.router.navigate(['/']);
            } else {
                // TODO: Handle auth failure
            }
        });
    }

    getAuthUser(): User | null {
        const authUser = localStorage.getItem(CONSTANTS.AUTH_USER);
        if (authUser) {
            return JSON.parse(authUser);
        }
        return null;
    }

    getAuthUserId(): string | null {
        const authUser = localStorage.getItem(CONSTANTS.AUTH_USER);
        if (authUser) {
            return JSON.parse(authUser)["id"];
        }
        return null;
    }

    logout() {
        localStorage.removeItem(CONSTANTS.ACCESS_TOKEN);
        localStorage.removeItem(CONSTANTS.AUTH_USER);
    }

    getAccessToken(): string | null {
        return localStorage.getItem(CONSTANTS.ACCESS_TOKEN);
    }
}
