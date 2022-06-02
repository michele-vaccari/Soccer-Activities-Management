import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Lineup } from '../interfaces/lineup';
import { Player } from '../interfaces/player';
import { Team } from '../interfaces/team';
import { AuthenticationService } from '../services/authentication.service';
import { ReportService } from '../services/report.service';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-lineup',
  templateUrl: './lineup.component.html',
  styleUrls: ['./lineup.component.css']
})
export class LineupComponent implements OnInit {

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
      columnDef: 'role',
      header: $localize `:@@ROLE:Role`,
      cell: (element: Player) => `${element.role}`,
    }
  ];
  displayedColumns = this.columns.map(c => c.columnDef).concat(['actions']);
  dataSource = new MatTableDataSource<Player>();
  teamName?: string;
  MAIN_PLAYER_NUMBER: number = 11;
  RESERVE_PLAYER_NUMBER: number = 7;
  reportId: number = 0;

  postLineup() {
    const lineup: Lineup = {
      teamId: this.authenticationService.getTeamId(),
      mainPlayerIds: [...this.mainPlayers],
      reservePlayerIds: [...this.reservePlayers]
    };
    this.reportService.addLineup(this.reportId, lineup).subscribe(
      {
        error: (e) => {
          let message = $localize `:@@ERROR_ADDING_LINEUP:Error adding lineup`;

          if (e.status == 404)
            message = $localize `:@@ERROR_ADDING_LINEUP_REPORT_NOT_FOUND:Error adding lineup, report not found`;
          
          this.snackBar.open(message);
        },
        complete: () => {
          this.snackBar.open($localize `:@@LINEUP_SUCCESSFULLY_ADDED:Lineup successfully added`);
          this.router.navigate(['/teams', this.authenticationService.getTeamId(), 'matches']);
        }
      }
    );
  }

  constructor(private teamService: TeamService,
              public authenticationService: AuthenticationService,
              private reportService: ReportService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {

    const id = this.route.snapshot.paramMap.get('id');

    if (id == null)
      return;

    this.reportId = parseInt(id);

    this.teamService.getTeam(this.authenticationService.getTeamId()).subscribe(
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

    this.getAllPlayers()
  }

  getAllPlayers() {
    this.teamService.getAllPlayersOfTeam(this.authenticationService.getTeamId()).subscribe(
      {
        next: (players: Player[]) => { this.dataSource.data = players.filter(player => player.active == 'Y'); },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_PLAYERS_OF_TEAM_TEAM_NOT_FOUND:Error retrieving players of team, team not found`);
          this.router.navigate(['teams']);
        }
      }
    );
  }

  mainPlayers = new Set<number>();
  reservePlayers = new Set<number>();

  onClickMainPlayerCheckBox(event: any, elementId: number) {
    if (this.mainPlayers.size == this.MAIN_PLAYER_NUMBER &&
        !this.mainPlayers.has(elementId))
      event.preventDefault();
  }

  onClickReservePlayerCheckBox(event: any, elementId: number) {
    if (this.reservePlayers.size >= this.RESERVE_PLAYER_NUMBER &&
       !this.reservePlayers.has(elementId))
      event.preventDefault();
  }

  onMainPlayer(checked: boolean, playerId: number) {
    if (!checked) {
      this.mainPlayers.delete(playerId);
      return;
    }

    if (this.reservePlayers.has(playerId))
      this.reservePlayers.delete(playerId);

    this.mainPlayers.add(playerId)
  }

  onReservePlayer(checked: boolean, playerId: number) {
    if (!checked) {
      this.reservePlayers.delete(playerId);
      return;
    }
    
    if (this.mainPlayers.has(playerId))
      this.mainPlayers.delete(playerId);

    this.reservePlayers.add(playerId)
  }

  ngOnInit(): void {
  }

}
