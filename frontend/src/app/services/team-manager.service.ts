import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class TeamManagerService {

  constructor(private http: HttpClient) { }

  getAllTeamManagers(): Observable<User[]> {
    return this.http.get<User[]>(
      API_ENDPOINT + '/teammanagers'
      );
  }

  getTeamManager(id: number): Observable<User> {
    return this.http.get<User>(
      API_ENDPOINT + '/teammanagers/' + id
      );
  }

  addTeamManager(user: User): Observable<User> {
    return this.http.post<User>(
      API_ENDPOINT + '/teammanagers',
      user,
      this.httpOptionsContentTypeJson
    );
  }
  
  updateTeamManager(id: number, user: User): Observable<User> {
    return this.http.patch<User>(
      API_ENDPOINT + '/teammanagers/' + id,
      user,
      this.httpOptionsContentTypeJson
    );
  }

  deactivateTeamManager(id: number) {
    return this.http.delete(
      API_ENDPOINT + '/teammanagers/' + id
    );
  }
  
  private httpOptionsContentTypeJson = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
}
