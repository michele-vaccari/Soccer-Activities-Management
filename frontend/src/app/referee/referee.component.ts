import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../interfaces/user';
import { RefereeService } from '../services/referee.service';

@Component({
  selector: 'app-referee',
  templateUrl: './referee.component.html',
  styleUrls: ['./referee.component.css']
})
export class RefereeComponent implements OnInit {

  postNewReferee() {
    const user: User = {
      name: this.userForm.controls['name'].value,
      surname: this.userForm.controls['surname'].value,
      address: this.userForm.controls['address'].value,
      birthDate: this.userForm.controls['birthDate'].value,
      phone: this.userForm.controls['phone'].value,
      citizenship: this.userForm.controls['citizenship'].value,
      resume: this.userForm.controls['resume'].value,
      email: this.userForm.controls['email'].value,
      password: this.userForm.controls['password'].value
    };
    this.refereeService.addReferee(user).subscribe(
      {
        error: () => {
          this.snackBar.open($localize `:@@ERROR_ADDING_REFEREE:Error adding referee`);
        },
        complete: () => {
          this.snackBar.open($localize `:@@REFEREE_SUCCESSFULLY_ADDED:Referee successfully added`);
          this.router.navigate(['/referees']);
        }
      }
    );
  }

  updateReferee() {
    if (this.id == null)
      return;

    const user: User = {
      name: this.userForm.controls['name'].value,
      surname: this.userForm.controls['surname'].value,
      address: this.userForm.controls['address'].value,
      birthDate: this.userForm.controls['birthDate'].value,
      phone: this.userForm.controls['phone'].value,
      citizenship: this.userForm.controls['citizenship'].value,
      resume: this.userForm.controls['resume'].value,
      email: this.userForm.controls['email'].value,
      password: this.userForm.controls['password'].value
    };

    this.refereeService.updateReferee(this.id, user).subscribe(
      {
        error: (e) => {
          let message = $localize `:@@ERROR_UPDATING_REFEREE:Error updating referee`;

        if (e.status == 404)
          message = $localize `:@@ERROR_UPDATING_REFEREE_REFEREE_NOT_FOUND:Error updating referee, referee not found`;
        else if (e.status == 409)
          message = $localize `:@@ERROR_UPDATING_REFEREE_EMAIL_HAS_ALREADY_BEEN_USED:Error updating referee, email has already been used`;
        this.snackBar.open(message);
        },
        complete: () => {
          this.snackBar.open($localize `:@@REFEREE_SUCCESSFULLY_UPDATED:Referee successfully updated`);
        }
      }
    );
  }

  getEmailErrorMessage() {
    if (this.userForm.controls['email'].hasError('required'))
      return $localize `:@@YOU_MUST_ENTER_A_VALUE:You must enter a value`;

    return this.userForm.controls['email'].hasError('email') ? $localize `:@@NOT_A_VALID_EMAIL:Not a valid email` : '';
  }

  getBirthDateErrorMessage() {
    if (this.userForm.controls['birthDate'].hasError('required'))
      return $localize `:@@YOU_MUST_ENTER_A_VALUE:You must enter a value`;

    return this.userForm.controls['birthDate'].hasError('pattern') ? $localize `:@@NOT_A_VALID_DATE:Not a valid date. Accepted date format: DD-MM-YYYY` : '';
  }

  constructor(private refereeService: RefereeService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {
    const id = this.route.snapshot.paramMap.get('id');
    this.isUpdateMode = id != null;

    if (id == null)
      return;

    this.id = +id;

    this.refereeService.getReferee(this.id).subscribe(
      {
        next: (user: User) => {
          this.userForm.controls['name'].setValue(user.name);
          this.userForm.controls['surname'].setValue(user.surname);
          this.userForm.controls['address'].setValue(user.address);
          this.userForm.controls['birthDate'].setValue(user.birthDate);
          this.userForm.controls['phone'].setValue(user.phone);
          this.userForm.controls['citizenship'].setValue(user.citizenship);
          this.userForm.controls['resume'].setValue(user.resume);
          this.userForm.controls['email'].setValue(user.email);
          this.userForm.controls['password'].setValue(user.password);
        },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_REFEREE_REFEREE_NOT_FOUND:Error retrieving referee, referee not found`);
          this.router.navigate(['referees']);
        }
      }
    );
  }

  ngOnInit(): void {
  }

  isAdminUser: boolean = false;
  isUpdateMode: boolean = false;
  hidePassword = true;

  areUserFormValid() {

    return this.userForm.controls['name'].valid &&
      this.userForm.controls['surname'].valid &&
      this.userForm.controls['address'].valid &&
      this.userForm.controls['birthDate'].valid &&
      this.userForm.controls['phone'].valid &&
      this.userForm.controls['citizenship'].valid &&
      this.userForm.controls['resume'].valid &&
      this.userForm.controls['email'].valid &&
      this.userForm.controls['password'].valid;
  }

  areUserFormWithValueValid() {
    return this.formHasValueAndIsValid('name') &&
      this.formHasValueAndIsValid('surname') &&
      this.formHasValueAndIsValid('address') &&
      this.formHasValueAndIsValid('birthDate') &&
      this.formHasValueAndIsValid('phone') &&
      this.formHasValueAndIsValid('citizenship') &&
      this.formHasValueAndIsValid('resume') &&
      this.formHasValueAndIsValid('email') &&
      this.formHasValueAndIsValid('password');
  }

  userForm = new FormGroup({
    name: new FormControl('', Validators.required),
    surname: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    birthDate: new FormControl('', [Validators.required, Validators.pattern('^([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2})$')]),
    phone: new FormControl('', Validators.required),
    citizenship: new FormControl('', Validators.required),
    resume: new FormControl('', Validators.required),
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
