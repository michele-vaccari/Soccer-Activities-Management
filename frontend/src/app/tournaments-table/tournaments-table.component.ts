import { Component, OnInit } from '@angular/core';
import { TournamentService } from '../services/tournament.service';
import { ShortTournament } from '../interfaces/short-tournament';
import { MatTableDataSource } from '@angular/material/table';
import { TeamService } from '../services/team.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-tournaments-table',
  templateUrl: './tournaments-table.component.html',
  styleUrls: ['./tournaments-table.component.css']
})
export class TournamentsTableComponent implements OnInit {

  columns = [
    {
      columnDef: 'name',
      header: $localize `:@@TOURNAMENT_NAME:Tournament name`,
      cell: (element: ShortTournament) => `${element.name}`,
    },
    {
      columnDef: 'type',
      header: $localize `:@@TYPE:Type`,
      cell: (element: ShortTournament) => `${element.type == "RoundRobin" ? $localize `:@@ROUND_ROBIN:Round Robin` : $localize `:@@SINGLE_ELIMINATION:Single Elimination`}`,
    }
  ];
  displayedColumns = this.columns.map(c => c.columnDef).concat(['actions']);
  dataSource = new MatTableDataSource<ShortTournament>();

  constructor(private tournamentService: TournamentService,
              private teamService: TeamService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {
    const id = this.route.snapshot.paramMap.get('id');

    if (id == null)
      return;

    this.id = +id;
    this.hasTeamId = true;
  }

  getAllTournaments() {
    this.tournamentService.getAllTournaments().subscribe((data: ShortTournament[]) => this.dataSource.data = data);
  }

  getAllTournamentsOfTeam(teamId: number) {
    this.teamService.getAllTournamentsOfTeam(teamId).subscribe(
      {
        next: (data: ShortTournament[]) => { this.dataSource.data = data },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_TOURNAMENTS_OF_TEAM_TEAM_NOT_FOUND:Error retrieving tournaments of team, team not found`);
          this.router.navigate(['teams']);
        }
      }
    );
  }

  ngOnInit() {
    this.hasTeamId ? this.getAllTournamentsOfTeam(this.id) : this.getAllTournaments();
  }

  private id: number = 0;
  private hasTeamId: boolean = false;

}
