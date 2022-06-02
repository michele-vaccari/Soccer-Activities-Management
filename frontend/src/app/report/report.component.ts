import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Match } from '../interfaces/match';
import { Tournament } from '../interfaces/tournament';
import { TournamentMatch } from '../interfaces/tournament-match';
import { User } from '../interfaces/user';
import { MatchService } from '../services/match.service';
import { RefereeService } from '../services/referee.service';
import { TournamentService } from '../services/tournament.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

  tournaments?: Tournament[];
  showPhase: boolean = false;
  phases?: any[] = Array<any>();
  tournament?: Tournament;
  showMatch: boolean = false;
  matches: TournamentMatch[] = new Array<TournamentMatch>();
  showOtherFields: boolean = false;
  referees?: User[];
  toBeDefined: string = $localize `:@@TO_BE_DEFINED:To be defined`;

  postNewTournament() {
    const tournament: Tournament = {
      name: this.reportForm.controls['name'].value,
      type: this.reportForm.controls['type'].value,
      description: this.reportForm.controls['description'].value
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

  onTournamentChange(teamId: number) {
    this.showPhase = teamId != undefined && teamId != null;

    this.tournamentService.getTournament(teamId).subscribe((tournament: Tournament) => {
      this.tournament = tournament;
      tournament.type == 'R' ? this.phases = this.getDaysFromRoundRobinTournament(tournament) : this.phases = this.getPhasesFromSingleEliminationTournament(tournament);
    });
  }

  onPhaseChange(phaseValue: number) {
    this.showMatch = phaseValue != undefined && phaseValue != null;

    if (this.tournament == undefined)
      return;

    if (this.tournament.type == 'R') {
      
      if (this.tournament.firstRoundMatches == null ||
          this.tournament.secondRoundMatches == null)
        return;

      let phases = Object.keys(this.tournament.firstRoundMatches).length * 2;
      if (phaseValue > phases / 2)
        this.matches = this.getMatchesWithoutReferee((new Map(Object.entries(this.tournament.secondRoundMatches))).get(phaseValue.toString()));
      else
        this.matches = this.getMatchesWithoutReferee((new Map(Object.entries(this.tournament.firstRoundMatches))).get(phaseValue.toString()));
    }
    else {
      if (phaseValue == 8)
        this.matches = this.getMatchesWithoutReferee(this.getMatches(this.tournament.eighthFinalsMatches));
      else if (phaseValue == 4)
        this.matches = this.getMatchesWithoutReferee(this.getMatches(this.tournament.quarterFinalsMatches));
      else if (phaseValue == 2)
        this.matches = this.getMatchesWithoutReferee(this.getMatches(this.tournament.semifinalMatches));
      else if (phaseValue == 1 && this.tournament.finalMatch != undefined)
        this.matches = this.getMatchesWithoutReferee([this.tournament.finalMatch]);
    }
  }

  onMatchesChange(matchValue: number) {
    this.showOtherFields = matchValue != undefined && matchValue != null;

    this.refereeService.getAllReferees().subscribe((referees: User[]) => {
      this.referees = referees;
    });
  }

  getMatches(map: Map<number, TournamentMatch[]> | undefined) {
    let matches: TournamentMatch[] = new Array<TournamentMatch>();
    
    if (map == undefined)
      return matches;
    
    for (const [key, value] of Object.entries(map))
      matches.push(value[0]);
    
    return matches;
  }

  getMatchesWithoutReferee(tournaments: TournamentMatch[]) {
    let result: TournamentMatch[] = new Array<TournamentMatch>();
    tournaments.forEach(match => {
      if (match.reportId === undefined)
        result.push(match);
    });
    return result;
  }

  getPhasesFromSingleEliminationTournament(tournament: Tournament) {

    let hasEighthFinals = false;
    let hasQuarterFinals = false;
    let hasSemifinal = false;

    if (tournament.eighthFinalsMatches != undefined &&
        tournament.quarterFinalsMatches != undefined &&
        tournament.semifinalMatches != undefined) {
      hasEighthFinals = Object.keys(tournament.eighthFinalsMatches).length > 0;
      hasQuarterFinals = Object.keys(tournament.quarterFinalsMatches).length > 0;
      hasSemifinal = Object.keys(tournament.semifinalMatches).length > 0;
    }

    let phases: any [] = new Array<any>();
    if (hasEighthFinals)
      phases.push({value: 8, displayValue: $localize `:@@EIGHTH_FINAL:Eighth final`});
    if (hasQuarterFinals)
      phases.push({value: 4, displayValue:$localize `:@@QUARTER_FINAL:Quarter final`});
    if (hasSemifinal)
      phases.push({value: 2, displayValue:$localize `:@@SEMI_FINAL:Semi final`});
    
    phases.push({value: 1, displayValue:$localize `:@@FINAL:Final`})

    return phases;
  }

  private getDaysFromRoundRobinTournament(tournament: Tournament) {
    if (tournament.firstRoundMatches == undefined ||
        tournament.secondRoundMatches == undefined)
        return;
    
    let firstRoundKeys = this.getKeysFormMap(tournament.firstRoundMatches, false);
    let secondRoundKeys = this.getKeysFormMap(tournament.firstRoundMatches, true);

    let keys = firstRoundKeys.concat(secondRoundKeys);
    let days: any [] = new Array<any>();
    keys.forEach(key => {
      days.push({value: key, displayValue:'Day ' + key});
    });

    return days;
  }
  
  private getKeysFormMap(map: Map<number, TournamentMatch[]>, isSecondRound: boolean) {
    let keys: number[] = new Array<number>();
    
    if (map == undefined)
      return keys;
    
    for (const [key, value] of Object.entries(map))
      keys.push(isSecondRound ? parseInt(key) + Object.keys(map).length : parseInt(key));
    
    return keys
  }

  constructor(private tournamentService: TournamentService,
              private refereeService: RefereeService,
              private matchService: MatchService,
              private snackBar: MatSnackBar,
              private router: Router) {
    this.tournamentService.getAllTournaments().subscribe((tournaments: Tournament[]) => { this.tournaments = tournaments });
  }

  getDateErrorMessage() {
    if (this.reportForm.controls['matchDate'].hasError('required'))
      return $localize `:@@YOU_MUST_ENTER_A_VALUE:You must enter a value`;

    return this.reportForm.controls['matchDate'].hasError('pattern') ? $localize `:@@NOT_A_VALID_DATE:Not a valid date. Accepted date format: DD-MM-YYYY` : '';
  }

  getTimeErrorMessage() {
    if (this.reportForm.controls['matchTime'].hasError('required'))
      return $localize `:@@YOU_MUST_ENTER_A_VALUE:You must enter a value`;

    return this.reportForm.controls['matchTime'].hasError('pattern') ? $localize `:@@NOT_A_VALID_TIME:Not a valid time. Accepted time format: HH:MM` : '';
  }

  ngOnInit(): void {
  }

  updateMatch() {
    const matchId = this.reportForm.controls['match'].value;

    const match: Match = {
      date: this.reportForm.controls['matchDate'].value,
      time: this.reportForm.controls['matchTime'].value,
      place: this.reportForm.controls['place'].value,
      refereeId: this.reportForm.controls['referee'].value
    };

    this.matchService.updateMatch(matchId, match).subscribe(
      {
        error: (e) => {
          let message = $localize `:@@ERROR_UPDATING_MATCH:Error updating match`;

        if (e.status == 404)
          message = $localize `:@@ERROR_UPDATING_MATCH_MATCH_NOT_FOUND:Error updating match, match not found`;
        else if (e.status == 409)
          message = $localize `:@@ERROR_UPDATING_MATCH_UPDATE_HAS_ALREADY_BEEN_MADE:Error updating match, a request for an update has already been made`;
        this.snackBar.open(message);
        },
        complete: () => {
          this.snackBar.open($localize `:@@MATCH_SUCCESSFULLY_UPDATED:Match successfully updated`);
          this.showPhase = false;
          this.showMatch = false;
          this.showOtherFields = false;
          this.reportForm.reset();
        }
      }
    );
    if (this.tournament !== undefined &&
        this.tournament.id !== undefined)
      this.onTournamentChange(this.tournament.id);
  }

  reportForm = new FormGroup({
    tournament: new FormControl('', Validators.required),
    phase: new FormControl('', Validators.required),
    match: new FormControl('', Validators.required),
    referee: new FormControl('', Validators.required),
    matchDate: new FormControl('', [Validators.required, Validators.pattern('^([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2})$')]),
    matchTime: new FormControl('', [Validators.required, Validators.pattern("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")]),
    place: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required)
  }, );

}
