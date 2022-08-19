import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class RefereeService {

  constructor(private http: HttpClient) { }

  getAllReferees(): Observable<User[]> {
    return this.http.get<User[]>(
      API_ENDPOINT + '/referees'
      );
  }

  getReferee(id: number): Observable<User> {
    return this.http.get<User>(
      API_ENDPOINT + '/referees/' + id
      );
  }

  addReferee(user: User): Observable<User> {
    return this.http.post<User>(
      API_ENDPOINT + '/referees',
      user,
      this.httpOptionsContentTypeJson
    );
  }
  
  updateReferee(id: number, user: User): Observable<User> {
    return this.http.patch<User>(
      API_ENDPOINT + '/referees/' + id,
      user,
      this.httpOptionsContentTypeJson
    );
  }

  deactivateReferee(id: number) {
    return this.http.delete(
      API_ENDPOINT + '/referees/' + id
    );
  }
  
  private httpOptionsContentTypeJson = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
}
