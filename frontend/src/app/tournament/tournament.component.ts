import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { RankingLine } from '../interfaces/ranking-line';
import { Tournament } from '../interfaces/tournament';
import { TournamentMatch } from '../interfaces/tournament-match';
import { TournamentService } from '../services/tournament.service';

@Component({
  selector: 'app-tournament',
  templateUrl: './tournament.component.html',
  styleUrls: ['./tournament.component.css']
})
export class TournamentComponent implements OnInit {

  tournament?: Tournament;
  isRoundRobinTournament?: boolean;
  firstRoundDayMatchMap: Map<number, TournamentMatch[]> = new Map<number, TournamentMatch[]>();
  secondRoundDayMatchMap: Map<number, TournamentMatch[]> = new Map<number, TournamentMatch[]>()
  
  hasEighthFinals?: boolean;
  hasQuarterFinals?: boolean;
  hasSemifinal?: boolean;

  eighthFinalsMatches: Map<number, TournamentMatch[]> = new Map<number, TournamentMatch[]>();
	quarterFinalsMatches: Map<number, TournamentMatch[]> = new Map<number, TournamentMatch[]>();
	semiFinalsMatches: Map<number, TournamentMatch[]> = new Map<number, TournamentMatch[]>();
  finalMatch: Map<number, TournamentMatch[]> = new Map<number, TournamentMatch[]>();

  eighthFinalStageName: string = $localize `:@@EIGHTH_FINAL:Eighth final`;
  quarterFinalStageName: string = $localize `:@@QUARTER_FINAL:Quarter final`;
  semiFinalStageName: string = $localize `:@@SEMI_FINAL:Semi final`;
  finalStageName: string = $localize `:@@FINAL:Final`;

  columns = [
    {
      columnDef: 'position',
      header: $localize `:@@POSITION:Position`,
      cell: (element: RankingLine) => `${element.position}`
    },
    {
      columnDef: 'teamName',
      header: $localize `:@@TEAM_NAME:Team name`,
      cell: (element: RankingLine) => `${element.teamName}`,
    },
    {
      columnDef: 'playedMatches',
      header: $localize `:@@PLAYED_MATCHES:Played matches`,
      cell: (element: RankingLine) => `${element.playedMatches}`,
    },
    {
      columnDef: 'wonMatches',
      header: $localize `:@@WON_MATCHES:Won matches`,
      cell: (element: RankingLine) => `${element.wonMatches}`,
    },
    {
      columnDef: 'lostMatches',
      header: $localize `:@@LOST_MATCHES:Lost matches`,
      cell: (element: RankingLine) => `${element.lostMatches}`,
    },
    {
      columnDef: 'goalsMade',
      header: $localize `:@@GOALS_MADE:Goals made`,
      cell: (element: RankingLine) => `${element.goalsMade}`,
    },
    {
      columnDef: 'goalsSuffered',
      header: $localize `:@@GOALS_SUFFERED:Goals suffered`,
      cell: (element: RankingLine) => `${element.goalsSuffered}`,
    }
  ];
  displayedColumns = this.columns.map(c => c.columnDef);
  dataSource = new MatTableDataSource<RankingLine>();

  constructor(private tournamentService: TournamentService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {
    const id = this.route.snapshot.paramMap.get('id');

    if (id == null)
      return;

    this.id = +id;

    this.tournamentService.getTournament(this.id).subscribe(
      {
        next: (tournament: Tournament) => {
          this.tournament = tournament;
          if (tournament.ranking !== undefined)
            this.dataSource.data = tournament.ranking;
          this.isRoundRobinTournament = tournament.type == "R";

          if (this.isRoundRobinTournament) {

            this.columns.push({
              columnDef: 'score',
              header: $localize `:@@SCORE:Score`,
              cell: (element: RankingLine) => `${element.score}`,
            },
            {
              columnDef: 'tiedMatches',
              header: $localize `:@@TIED_MATCHES:Tied matches`,
              cell: (element: RankingLine) => `${element.tiedMatches}`,
            }
            );
            this.displayedColumns = this.columns.map(c => c.columnDef);

            if (this.tournament.firstRoundMatches !== undefined)
              this.firstRoundDayMatchMap = this.tournament.firstRoundMatches;
            if (this.tournament.secondRoundMatches !== undefined)
              this.secondRoundDayMatchMap = this.tournament.secondRoundMatches;
          }
          else {
            if (tournament.eighthFinalsMatches !== undefined)
              this.eighthFinalsMatches = tournament.eighthFinalsMatches;
            if (tournament.quarterFinalsMatches !== undefined)
              this.quarterFinalsMatches = tournament.quarterFinalsMatches;
            if (tournament.semifinalMatches !== undefined)
              this.semiFinalsMatches = tournament.semifinalMatches;
            if (tournament.finalMatch !== undefined)
              this.finalMatch = new Map<number, TournamentMatch[]>([
                [parseInt(tournament.finalMatch.matchName), [tournament.finalMatch]]
              ]);

            this.hasEighthFinals = Object.keys(this.eighthFinalsMatches).length > 0;
            this.hasQuarterFinals = Object.keys(this.quarterFinalsMatches).length > 0;
            this.hasSemifinal = Object.keys(this.semiFinalsMatches).length > 0;
          }
        },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_TOURNAMENT_TOURNAMENT_NOT_FOUND:Error retrieving tournament, tournament not found`);
          this.router.navigate(['tournaments']);
        }
      }
    );
  }

  ngOnInit(): void {
  }

  private id?: number;
}
