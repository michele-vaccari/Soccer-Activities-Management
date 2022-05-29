import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(
      API_ENDPOINT + '/admins/' + id
      );
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.patch<User>(
      API_ENDPOINT + '/admins/' + id,
      user,
      this.httpOptionsContentTypeJson
      );
  }

  private httpOptionsContentTypeJson = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
}
