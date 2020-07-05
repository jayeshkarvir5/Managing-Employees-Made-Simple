import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Project } from '../../modules/auth/models/project.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  httpOptions = {
    headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin' : 'http://localhost:8080',
    'Access-Control-Allow-Methods' : 'GET, POST, PUT, DELETE'
    })
  };
  url:string = "http://localhost:8080/projects";

  http:HttpClient;
  constructor(http: HttpClient) {
    this.http = http;
  }

  public getAllProjects():Observable<Project []>{
    return this.http.get<Project []>(this.url,this.httpOptions);
  }

  public getProjectById(projectId:string):Observable<Project>{
    return this.http.get<Project>(this.url+"/"+projectId,this.httpOptions);
  }

  public saveProject(projectData:Project){
    this.http.post(this.url,projectData,this.httpOptions);
  }

  public updateProject(projectData:Project){
    this.http.put(this.url,projectData,this.httpOptions);
  }

  public deleteProjectById(projectId:string){
    this.http.delete(this.url+"/"+projectId,this.httpOptions);
  }

}
