import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Team } from '../interfaces/team';
import { Tournament } from '../interfaces/tournament';
import { TeamService } from '../services/team.service';
import { TournamentService } from '../services/tournament.service';

@Component({
  selector: 'app-create-tournament',
  templateUrl: './create-tournament.component.html',
  styleUrls: ['./create-tournament.component.css']
})
export class CreateTournamentComponent implements OnInit {

  teams?: any[] = new Array<any>();

  getSelectedTeamsId() {
    if (this.teams !== undefined)
      return this.teams.filter(x => x.checked === true).map(x => x.id);
    
    return undefined;
  }

  postNewTournament() {
    const tournament: Tournament = {
      name: this.tournamentForm.controls['name'].value,
      type: this.tournamentForm.controls['type'].value,
      description: this.tournamentForm.controls['description'].value,
      teamIds: this.getSelectedTeamsId()
    };
    this.tournamentService.addTournament(tournament).subscribe(
      {
        error: () => {
          this.snackBar.open($localize `:@@ERROR_ADDING_TOURNAMENT:Error adding tournament`);
        },
        complete: () => {
          this.snackBar.open($localize `:@@TOURNAMENT_SUCCESSFULLY_ADDED:Tournament successfully added`);
          this.router.navigate(['/tournaments']);
        }
      }
    );
  }

  constructor(private teamService: TeamService,
              private tournamentService: TournamentService,
              private snackBar: MatSnackBar,
              private router: Router) {
    this.teamService.getAllTeams().subscribe(
      {
        next: (teams: Team[]) => {
          teams.forEach(team =>
            {
              if (this.teams !== undefined)
                this.teams.push({id: team.id, name: team.name, checked: false})
            }
          );
        }
      }
    );
  }

  ngOnInit(): void {
  }

  areTournamentFormValid() {
    let selectedTeamIds = this.getSelectedTeamsId();
    let hasValidRoundRobinTournamentSelectedTeamIds = selectedTeamIds === undefined ? false : selectedTeamIds.length >= 2;

    let hasValidSingleEliminationTournamentSelectedTeamsIds = selectedTeamIds === undefined ? false : (selectedTeamIds.length == 2 || selectedTeamIds.length == 4 || selectedTeamIds.length == 8 || selectedTeamIds.length == 16);

    return this.tournamentForm.controls['name'].valid &&
      this.tournamentForm.controls['description'].valid &&
      (this.tournamentForm.controls['type'].value == 'RoundRobin' ? hasValidRoundRobinTournamentSelectedTeamIds : hasValidSingleEliminationTournamentSelectedTeamsIds);
  }

  tournamentForm = new FormGroup({
    type: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required)
  }, );

}
