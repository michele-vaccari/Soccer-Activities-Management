import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { User } from '../interfaces/user';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  postNewUser() {
    const user: User = {
      role: this.userForm.controls['role'].value,
      name: this.userForm.controls['name'].value,
      surname: this.userForm.controls['surname'].value,
      email: this.userForm.controls['email'].value,
      password: this.userForm.controls['password'].value
    };
    this.userService.addUser(user).subscribe(
      {
        error: () => {
          this.snackBar.open($localize `:@@ERROR_ADDING_USER:Error adding user`);
        },
        complete: () => {
          this.snackBar.open($localize `:@@USER_SUCCESSFULLY_ADDED:User successfully added`);
          this.router.navigate(['/users']);
        }
      }
    );
  }

  updateUser() {
    if (this.id == null)
      return;

    if (this.isAdminUser) {
      const admin: User = {
        name: this.userForm.controls['name'].value,
        surname: this.userForm.controls['surname'].value,
        email: this.userForm.controls['email'].value,
        password: this.userForm.controls['password'].value
      };

      this.adminService.updateUser(this.id, admin).subscribe(
        {
          error: (e) => {
            let message = $localize `:@@ERROR_UPDATING_USER:Error updating user`;
  
          if (e.status == 404)
            message = $localize `:@@ERROR_UPDATING_USER_USER_NOT_FOUND:Error updating user, user not found`;
          else if (e.status == 409)
            message = $localize `:@@ERROR_UPDATING_USER_EMAIL_HAS_ALREADY_BEEN_USED:Error updating user, email has already been used`;
          this.snackBar.open(message);
          },
          complete: () => {
            this.snackBar.open($localize `:@@USER_SUCCESSFULLY_UPDATED:User successfully updated`);
          }
        }
      );
    }
    else {
      const user: User = {
        id: this.id,
        role: this.userForm.controls['role'].value,
        name: this.userForm.controls['name'].value,
        surname: this.userForm.controls['surname'].value,
        email: this.userForm.controls['email'].value,
        password: this.userForm.controls['password'].value,
        active: 'Y'
      };
  
      if (this.id == null)
        return;
  
      this.userService.updateUser(this.id, user).subscribe(
        {
          error: (e) => {
            let message = $localize `:@@ERROR_UPDATING_USER:Error updating user`;
  
          if (e.status == 404)
            message = $localize `:@@ERROR_UPDATING_USER_USER_NOT_FOUND:Error updating user, user not found`;
          else if (e.status == 409)
            message = $localize `:@@ERROR_UPDATING_USER_EMAIL_HAS_ALREADY_BEEN_USED:Error updating user, email has already been used`;
          this.snackBar.open(message);
          },
          complete: () => {
            this.snackBar.open($localize `:@@USER_SUCCESSFULLY_UPDATED:User successfully updated`);
            this.router.navigate(['users']);
          }
        }
      );
    }
  }

  getEmailErrorMessage() {
    if (this.userForm.controls['email'].hasError('required'))
      return $localize `:@@YOU_MUST_ENTER_A_VALUE:You must enter a value`;

    return this.userForm.controls['email'].hasError('email') ? $localize `:@@NOT_A_VALID_EMAIL:Not a valid email` : '';
  }

  constructor(private userService: UserService,
              private adminService: AdminService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {
    const id = this.route.snapshot.paramMap.get('id');
    this.isUpdateMode = id != null;

    this.isAdminUser = this.router.url.includes('admins');

    if (id == null)
      return;

    this.id = +id;

    if (this.isUpdateMode && this.isAdminUser) {
      this.adminService.getUser(this.id).subscribe(
        {
          next: (user: User) => {
            this.userForm.controls['name'].setValue(user.name);
            this.userForm.controls['surname'].setValue(user.surname);
            this.userForm.controls['email'].setValue(user.email);
          },
          error: () => {
            this.snackBar.open($localize `:@@ERROR_RETRIEVING_ADMIN_USER_NOT_FOUND:Error retrieving admin, admin not found`);
            this.router.navigate(['home']);
          }
        }
      );
    }
    else {
      this.userService.getUser(this.id).subscribe(
        {
          next: (user: User) => {
            this.userForm.controls['role'].setValue(user.role);
            this.userForm.controls['name'].setValue(user.name);
            this.userForm.controls['surname'].setValue(user.surname);
            this.userForm.controls['email'].setValue(user.email);
            this.userForm.controls['password'].setValue(user.password);
          },
          error: () => {
            this.snackBar.open($localize `:@@ERROR_RETRIEVING_USER_USER_NOT_FOUND:Error retrieving user, user not found`);
            this.router.navigate(['users']);
          }
        }
      );
    }
  }

  ngOnInit(): void {
  }

  isAdminUser: boolean = false;
  isUpdateMode: boolean = false;
  hidePassword = true;

  userRoles: any = [
    'Admin',
    'Referee',
    'TeamManager'
  ];

  userForm = new FormGroup({
    role: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    surname: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  });

  private id?: number;
}
