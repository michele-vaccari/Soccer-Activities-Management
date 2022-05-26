import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Player } from '../interfaces/player';
import { Team } from '../interfaces/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-team-players-table',
  templateUrl: './team-players-table.component.html',
  styleUrls: ['./team-players-table.component.css']
})
export class TeamPlayersTableComponent implements OnInit {
  columns = [
    {
      columnDef: 'name',
      header: $localize `:@@NAME:Name`,
      cell: (element: Player) => `${element.name}`,
    },
    {
      columnDef: 'surname',
      header: $localize `:@@SURNAME:Surname`,
      cell: (element: Player) => `${element.surname}`,
    },
    {
      columnDef: 'jerseyNumber',
      header: $localize `:@@JERSEY_NUMBER:Jersey number`,
      cell: (element: Player) => `${element.jerseyNumber}`,
    },
    {
      columnDef: 'birthDate',
      header: $localize `:@@BIRTH_DATE:Birth date`,
      cell: (element: Player) => `${element.birthDate}`,
    },
    {
      columnDef: 'role',
      header: $localize `:@@ROLE:Role`,
      cell: (element: Player) => `${element.role}`,
    }
  ];
  displayedColumns = this.columns.map(c => c.columnDef).concat(['actions']);
  dataSource = new MatTableDataSource<Player>();
  teamName?: string;

  constructor(private teamService: TeamService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {

    const id = this.route.snapshot.paramMap.get('id');

    if (id == null)
      return;

    this.teamService.getTeam(parseInt(id)).subscribe(
      {
        next: (team: Team) => {
          this.teamName = team.name;
        },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_TEAM_TEAM_NOT_FOUND:Error retrieving team, team not found`);
          this.router.navigate(['teams']);
        }
      }
      );

    this.teamService.getAllPlayersOfTeam(parseInt(id)).subscribe(
      {
        next: (players: Player[]) => { this.dataSource.data = players },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_PLAYERS_OF_TEAM_TEAM_NOT_FOUND:Error retrieving players of team, team not found`);
          this.router.navigate(['teams']);
        }
      }
    );
  }

  ngOnInit(): void {
  }

}
