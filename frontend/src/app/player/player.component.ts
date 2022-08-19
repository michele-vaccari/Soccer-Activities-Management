import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Player } from '../interfaces/player';
import { AuthenticationService } from '../services/authentication.service';
import { PlayerService } from '../services/player.service';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {

  postNewPlayer() {
    const player: Player = {
      teamId: this.authenticationService.getTeamId(),
      active: 'Y',
      name: this.playerForm.controls['name'].value,
      surname: this.playerForm.controls['surname'].value,
      birthDate: this.playerForm.controls['birthDate'].value,
      citizenship: this.playerForm.controls['citizenship'].value,
      jerseyNumber: this.playerForm.controls['jerseyNumber'].value,
      role: this.playerForm.controls['role'].value,
      description: this.playerForm.controls['description'].value,
    };
    this.playerService.addPlayer(player, this.authenticationService.getTeamId()).subscribe(
      {
        error: (e) => {
          let message = $localize `:@@ERROR_ADDING_PLAYER:Error adding player`;

          if (e.status == 406)
            message = $localize `:@@ERROR_ADDING_PLAYER_REACHED_MAXIMUM_LIMIT_OF_ACTIVE_PLAYERS:Error updating player, you have reached the maximum limit of active players per team`;
          
          this.snackBar.open(message);
        },
        complete: () => {
          this.snackBar.open($localize `:@@PLAYER_SUCCESSFULLY_ADDED:Player successfully added`);
          this.router.navigate(['/teams', this.authenticationService.getTeamId(), 'players']);
        }
      }
    );
  }

  updatePlayer() {
    if (this.id == null)
      return;

    const player: Player = {
      teamId: this.authenticationService.getTeamId(),
      active: 'Y',
      name: this.playerForm.controls['name'].value,
      surname: this.playerForm.controls['surname'].value,
      birthDate: this.playerForm.controls['birthDate'].value,
      citizenship: this.playerForm.controls['citizenship'].value,
      jerseyNumber: this.playerForm.controls['jerseyNumber'].value,
      role: this.playerForm.controls['role'].value,
      description: this.playerForm.controls['description'].value,
    };

    this.playerService.updatePlayer(this.id, player).subscribe(
      {
        error: (e) => {
          let message = $localize `:@@ERROR_UPDATING_PLAYER:Error updating player`;

        if (e.status == 404)
          message = $localize `:@@ERROR_UPDATING_PLAYER_PLAYER_NOT_FOUND:Error updating player, player not found`;
        this.snackBar.open(message);
        },
        complete: () => {
          this.snackBar.open($localize `:@@PLAYER_SUCCESSFULLY_UPDATED:Player successfully updated`);
          this.router.navigate(['/teams', this.authenticationService.getTeamId(), 'players']);
        }
      }
    );
  }

  getBirthDateErrorMessage() {
    if (this.playerForm.controls['birthDate'].hasError('required'))
      return $localize `:@@YOU_MUST_ENTER_A_VALUE:You must enter a value`;

    return this.playerForm.controls['birthDate'].hasError('pattern') ? $localize `:@@NOT_A_VALID_DATE:Not a valid date. Accepted date format: DD-MM-YYYY` : '';
  }

  constructor(private playerService: PlayerService,
              private authenticationService: AuthenticationService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {
    const id = this.route.snapshot.paramMap.get('id');
    this.isUpdateMode = id != null;

    if (id == null)
      return;

    this.id = +id;

    this.playerService.getPlayer(this.id).subscribe(
      {
        next: (player: Player) => {
          this.playerForm.controls['name'].setValue(player.name);
          this.playerForm.controls['surname'].setValue(player.surname);
          this.playerForm.controls['birthDate'].setValue(player.birthDate);
          this.playerForm.controls['citizenship'].setValue(player.citizenship);
          this.playerForm.controls['jerseyNumber'].setValue(player.jerseyNumber);
          this.playerForm.controls['role'].setValue(player.role);
          this.playerForm.controls['description'].setValue(player.description);
        },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_PLAYER_PLAYER_NOT_FOUND:Error retrieving player, player not found`);
          this.router.navigate(['team/', authenticationService.getTeamId(), 'players']);
        }
      }
    );
  }

  ngOnInit(): void {
  }
  
  isUpdateMode: boolean = false;

  arePlayerFormValid() {

    return this.playerForm.controls['name'].valid &&
      this.playerForm.controls['surname'].valid &&
      this.playerForm.controls['birthDate'].valid &&
      this.playerForm.controls['citizenship'].valid &&
      this.playerForm.controls['jerseyNumber'].valid &&
      this.playerForm.controls['role'].valid &&
      this.playerForm.controls['description'].valid;
  }

  arePlayerFormWithValueValid() {
    return this.formHasValueAndIsValid('name') &&
      this.formHasValueAndIsValid('surname') &&
      this.formHasValueAndIsValid('birthDate') &&
      this.formHasValueAndIsValid('citizenship') &&
      this.formHasValueAndIsValid('jerseyNumber') &&
      this.formHasValueAndIsValid('role') &&
      this.formHasValueAndIsValid('description');
  }

  playerForm = new FormGroup({
    name: new FormControl('', Validators.required),
    surname: new FormControl('', Validators.required),
    birthDate: new FormControl('', [Validators.required, Validators.pattern('^([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2})$')]),
    citizenship: new FormControl('', Validators.required),
    jerseyNumber: new FormControl('', Validators.required),
    role: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
  }, );

  private formHasValueAndIsValid (controlName: string) {
    if (this.playerForm.controls[controlName].value === null ||
        this.playerForm.controls[controlName].value === undefined)
      return true;
    
    return this.playerForm.controls[controlName].valid
  }

  private id?: number;
}
