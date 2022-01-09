import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Token } from '../interfaces/token';
import { API_ENDPOINT } from '../constants';
import { map } from 'rxjs/operators';
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private jwtHelper: JwtHelperService;

  constructor(private http: HttpClient) {
    this.jwtHelper = new JwtHelperService();
  }

  authenticate(email: string, password: string) {
    return this.http.post<Token>(
      API_ENDPOINT + '/authenticate',
      {email, password})
      .pipe(
        map(response => {
          sessionStorage.setItem('token', response.token);
          return response;
        })
      );
  }

  getUserEmail() {
    const token = sessionStorage.getItem('token');
    if (token === null)
      return;
    
    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken.email;
  }

  getUserRole() {
    const token = sessionStorage.getItem('token');
    if (token === null)
      return;
    
    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken.role;
  }

  isUserLoggedIn() {
    const token = sessionStorage.getItem('token');
    return token !== null &&
           !this.jwtHelper.isTokenExpired(token);
  }

  logOut() {
    sessionStorage.removeItem('token');
  }
}
