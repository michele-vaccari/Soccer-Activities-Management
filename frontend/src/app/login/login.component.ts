import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  authenticate() {
    const email: any = this.loginForm.controls['email'].value;
    const password: any = this.loginForm.controls['password'].value;

    this.authenticationService.authenticate(email, password).subscribe(
      {
        error: () => {
          this.snackBar.open($localize `:@@USER_AUTHENTICATION_ERROR:User authentication error`);
        },
        complete: () => {
          this.router.navigate(['/home']);
        }
      }
    );
  }

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private snackBar: MatSnackBar) { }

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  });

  ngOnInit(): void {
  }

}
