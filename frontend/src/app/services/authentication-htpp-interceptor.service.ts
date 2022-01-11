import { Observable, throwError } from 'rxjs';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from './authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationHtppInterceptorService implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (sessionStorage.getItem('token')) {
      request = request.clone({
        setHeaders: {
          'Authorization': `Bearer ${sessionStorage.getItem('token')}`
        }
      })
    }

    return next.handle(request).pipe(
      catchError((err: HttpErrorResponse) => this.handleError(err))
    );
  }

  constructor(private authenticationService: AuthenticationService,
              private snackBar: MatSnackBar) { }

  private handleError(error: HttpErrorResponse) {
    if (error.status == 401) {
      this.authenticationService.logOut();
      this.snackBar.open($localize `:@@USER_UNAUTHORIZED_ERROR:User unauthorized error`);
    }
    return throwError(() => error);
  }

}
