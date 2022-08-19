import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../interfaces/user';
import { TeamManagerService } from '../services/team-manager.service';

@Component({
  selector: 'app-team-manager',
  templateUrl: './team-manager.component.html',
  styleUrls: ['./team-manager.component.css']
})
export class TeamManagerComponent implements OnInit {

  postNewTeamManager() {
    const user: User = {
      name: this.userForm.controls['name'].value,
      surname: this.userForm.controls['surname'].value,
      address: this.userForm.controls['address'].value,
      phone: this.userForm.controls['phone'].value,
      teamName: this.userForm.controls['teamName'].value,
      email: this.userForm.controls['email'].value,
      password: this.userForm.controls['password'].value
    };
    this.teamManagerService.addTeamManager(user).subscribe(
      {
        error: () => {
          this.snackBar.open($localize `:@@ERROR_ADDING_TEAM_MANAGER:Error adding team manager`);
        },
        complete: () => {
          this.snackBar.open($localize `:@@TEAM_MANAGER_SUCCESSFULLY_ADDED:Team manager successfully added`);
          this.router.navigate(['/teammanagers']);
        }
      }
    );
  }

  updateTeamManager() {
    if (this.id == null)
      return;

    const user: User = {
      name: this.userForm.controls['name'].value,
      surname: this.userForm.controls['surname'].value,
      address: this.userForm.controls['address'].value,
      phone: this.userForm.controls['phone'].value,
      teamName: this.userForm.controls['teamName'].value,
      email: this.userForm.controls['email'].value,
      password: this.userForm.controls['password'].value
    };

    this.teamManagerService.updateTeamManager(this.id, user).subscribe(
      {
        error: (e) => {
          let message = $localize `:@@ERROR_UPDATING_TEAM_MANAGER:Error updating team manager`;

        if (e.status == 404)
          message = $localize `:@@ERROR_UPDATING_TEAM_MANAGER_TEAM_MANAGER_NOT_FOUND:Error updating team manager, team manager not found`;
        else if (e.status == 409)
          message = $localize `:@@ERROR_UPDATING_TEAM_MANAGER_EMAIL_HAS_ALREADY_BEEN_USED:Error updating team manager, email has already been used`;
        this.snackBar.open(message);
        },
        complete: () => {
          this.snackBar.open($localize `:@@TEAM_MANAGER_SUCCESSFULLY_UPDATED:Team manager successfully updated`);
        }
      }
    );
  }

  getEmailErrorMessage() {
    if (this.userForm.controls['email'].hasError('required'))
      return $localize `:@@YOU_MUST_ENTER_A_VALUE:You must enter a value`;

    return this.userForm.controls['email'].hasError('email') ? $localize `:@@NOT_A_VALID_EMAIL:Not a valid email` : '';
  }

  constructor(private teamManagerService: TeamManagerService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {
    const id = this.route.snapshot.paramMap.get('id');
    this.isUpdateMode = id != null;

    if (id == null)
      return;

    this.id = +id;

    this.teamManagerService.getTeamManager(this.id).subscribe(
      {
        next: (user: User) => {
          this.userForm.controls['name'].setValue(user.name);
          this.userForm.controls['surname'].setValue(user.surname);
          this.userForm.controls['address'].setValue(user.address);
          this.userForm.controls['phone'].setValue(user.phone);
          this.userForm.controls['teamName'].setValue(user.teamName);
          this.userForm.controls['email'].setValue(user.email);
          this.userForm.controls['password'].setValue(user.password);
        },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_TEAM_MANAGER_TEAM_MANAGER_NOT_FOUND:Error retrieving team manager, team manager not found`);
          this.router.navigate(['teammanagers']);
        }
      }
    );
  }

  ngOnInit(): void {
  }
  
  isUpdateMode: boolean = false;
  hidePassword = true;

  areUserFormValid() {

    return this.userForm.controls['name'].valid &&
      this.userForm.controls['surname'].valid &&
      this.userForm.controls['address'].valid &&
      this.userForm.controls['phone'].valid &&
      this.userForm.controls['teamName'].valid &&
      this.userForm.controls['email'].valid &&
      this.userForm.controls['password'].valid;
  }

  areUserFormWithValueValid() {
    return this.formHasValueAndIsValid('name') &&
      this.formHasValueAndIsValid('surname') &&
      this.formHasValueAndIsValid('address') &&
      this.formHasValueAndIsValid('phone') &&
      this.formHasValueAndIsValid('teamName') &&
      this.formHasValueAndIsValid('email') &&
      this.formHasValueAndIsValid('password');
  }

  userForm = new FormGroup({
    name: new FormControl('', Validators.required),
    surname: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    phone: new FormControl('', Validators.required),
    teamName: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  }, );

  private formHasValueAndIsValid (controlName: string) {
    if (this.userForm.controls[controlName].value === null ||
        this.userForm.controls[controlName].value === undefined)
      return true;
    
    return this.userForm.controls[controlName].valid
  }

  private id?: number;
}
