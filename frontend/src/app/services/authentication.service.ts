import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Token } from '../interfaces/token';
import { API_ENDPOINT } from '../constants';
import { map } from 'rxjs/operators';
import { JwtHelperService } from "@auth0/angular-jwt";
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

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

  getUserName() {
    const token = sessionStorage.getItem('token');
    if (token === null)
      return;
    
    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken.name;
  }

  getUserSurname() {
    const token = sessionStorage.getItem('token');
    if (token === null)
      return;
    
    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken.surname;
  }

  getUserId() {
    const token = sessionStorage.getItem('token');
    if (token === null)
      return;
    
    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken.id;
  }

  getTeamId() {
    const token = sessionStorage.getItem('token');
    if (token === null)
      return;
    
    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken.teamId;
  }

  isAdminUser() {
    return this.getUserRole() == 'Admin';
  }

  isTeamManagerUser() {
    return this.getUserRole() == 'TeamManager';
  }

  isRefereeUser() {
    return this.getUserRole() == 'Referee';
  }

  isUserLoggedIn() {
    const token = sessionStorage.getItem('token');
    return token !== null &&
           !this.jwtHelper.isTokenExpired(token);
  }

  logOut() {
    sessionStorage.removeItem('token');
    this.router.navigate(['home']);
  }

  constructor(private http: HttpClient,
              private router: Router) {
    this.jwtHelper = new JwtHelperService();
  }

  private getUserRole() {
    const token = sessionStorage.getItem('token');
    if (token === null)
      return;
    
    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken.role;
  }

  private jwtHelper: JwtHelperService;
}
