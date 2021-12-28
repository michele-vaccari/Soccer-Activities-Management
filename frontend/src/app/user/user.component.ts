import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { User } from '../interfaces/user';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  postNewUser() {
    const user: User = {
      type: this.userForm.controls['type'].value,
      name: this.userForm.controls['name'].value,
      surname: this.userForm.controls['surname'].value,
      email: this.userForm.controls['email'].value,
      password: this.userForm.controls['password'].value
    };
    this.userService.addUser(user).subscribe(
      {
        error: () => {
          this.snackBar.open('Error adding user');
        },
        complete: () => {
          this.snackBar.open(`User successfully added`);
          this.router.navigate(['/users']);
        }
      }
    );
  }

  updateUser() {
    const user: User = {
      id: this.id,
      type: this.userForm.controls['type'].value,
      name: this.userForm.controls['name'].value,
      surname: this.userForm.controls['surname'].value,
      email: this.userForm.controls['email'].value,
      password: this.userForm.controls['password'].value,
      isActive: 'Y'
    };

    if (this.id == null)
      return;

    this.userService.updateUser(this.id, user).subscribe(
      {
        error: (e) => {
          let message = 'Error updating user';

        if (e.status == 404)
          message = 'Error updating user, user not found'
        else if (e.status == 409)
          message = 'Error updating user, email has already been used'
        this.snackBar.open(message);
        },
        complete: () => {
          this.snackBar.open(`User successfully updated`);
          this.router.navigate(['users']);
        }
      }
    );
  }

  getEmailErrorMessage() {
    if (this.userForm.controls['email'].hasError('required'))
      return 'You must enter a value';

    return this.userForm.controls['email'].hasError('email') ? 'Not a valid email' : '';
  }

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {
    const id = this.route.snapshot.paramMap.get('id');
    this.isUpdateMode = id != null;

    if (id == null)
      return;

    this.id = +id;

    this.userService.getUser(this.id).subscribe(
      {
        next: (user: User) => {
          this.userForm.controls['type'].setValue(user.type);
          this.userForm.controls['name'].setValue(user.name);
          this.userForm.controls['surname'].setValue(user.surname);
          this.userForm.controls['email'].setValue(user.email);
          this.userForm.controls['password'].setValue(user.password);
        },
        error: () => {
          this.snackBar.open('Error retrieving user, user not found');
          this.router.navigate(['users']);
        }
      }
    );
  }

  ngOnInit(): void {
  }

  isUpdateMode: boolean = false;
  hidePassword = true;

  userTypes: any = [
    'SystemAdministrator',
    'Referee',
    'TeamManager'
  ];

  userForm = new FormGroup({
    type: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    surname: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  });

  private id?: number;
}
