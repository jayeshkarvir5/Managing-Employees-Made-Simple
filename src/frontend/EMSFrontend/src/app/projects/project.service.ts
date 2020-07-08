import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Project } from '../../modules/auth/models/project.model';

@Injectable({
    providedIn: 'root',
})
export class ProjectService {
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': 'http://localhost:8080',
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
        }),
    };
    url = 'http://localhost:8080/projects';

    constructor(private http: HttpClient) {}

    public async getAllProjects(): Promise<Project[] | undefined> {
        try {
            const projects: Project[] = await this.http
                .get<Project[]>(this.url, this.httpOptions)
                .toPromise();
            return projects;
        } catch (error) {
            console.log('Error! ', error);
        }
    }

    public getProjectById(projectId: string): Observable<Project> {
        return this.http.get<Project>(this.url + '/' + projectId, this.httpOptions);
    }

    public saveProject(projectData: Project) {
        this.http.post(this.url, projectData, this.httpOptions);
    }

    public updateProject(projectData: Project) {
        this.http.put(this.url, projectData, this.httpOptions);
    }

    public deleteProjectById(projectId: string) {
        this.http.delete(this.url + '/' + projectId, this.httpOptions);
    }
}
