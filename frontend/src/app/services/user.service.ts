import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../interfaces/user';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(
      API_ENDPOINT + '/users'
      );
  }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(
      API_ENDPOINT + '/users/' + id
      );
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(
      API_ENDPOINT + '/users',
      user,
      this.httpOptionsContentTypeJson
      );
    }
  
  updateUser(user: User): Observable<User> {
    return this.http.patch<User>(
      API_ENDPOINT + '/users',
      user,
      this.httpOptionsContentTypeJson
      );
    }
  
  deactivateUser(id: number) {
    return this.http.delete(
      API_ENDPOINT + '/users/' + id
      );
    }

  private httpOptionsContentTypeJson = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
}
