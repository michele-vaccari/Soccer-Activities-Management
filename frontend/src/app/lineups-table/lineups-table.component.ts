import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { LineupMatch } from '../interfaces/lineup-match';
import { AuthenticationService } from '../services/authentication.service';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-lineups-table',
  templateUrl: './lineups-table.component.html',
  styleUrls: ['./lineups-table.component.css']
})
export class LineupsTableComponent implements OnInit {

  columns = [
    {
      columnDef: 'id',
      header: $localize `:@@ID:Id`,
      cell: (element: LineupMatch) => `${element.id}`,
    },
    {
      columnDef: 'tournamentName',
      header: $localize `:@@TOURNAMENT_NAME:Tournament name`,
      cell: (element: LineupMatch) => `${element.tournamentName}`,
    },
    {
      columnDef: 'matchName',
      header: $localize `:@@MATCH:Match`,
      cell: (element: LineupMatch) => `${element.teamName === undefined ? this.toBeDefined : element.teamName} - ${element.otherTeamName === undefined ? this.toBeDefined : element.otherTeamName}`,
    },
    {
      columnDef: 'matchDate',
      header: $localize `:@@MATCH_DATE:Match date`,
      cell: (element: LineupMatch) => `${element.matchDate === undefined || element.matchDate == "" ? this.toBeDefined : element.matchDate}`,
    }
  ];
  displayedColumns = this.columns.map(c => c.columnDef).concat(['actions']);
  dataSource = new MatTableDataSource<LineupMatch>();
  teamId: number = 0;
  toBeDefined: string = $localize `:@@TO_BE_DEFINED:To be defined`;
  hasLineupsToCompile: boolean = false;

  constructor(private teamService: TeamService,
              public authenticationService: AuthenticationService,
              private route: ActivatedRoute) {

    const id = this.route.snapshot.paramMap.get('id');

    if (id == null)
      return;

    this.teamId = parseInt(id);

    this.teamService.getAllLineupsOfTeam(this.teamId).subscribe(
      (lineups: LineupMatch[]) =>
      {
        this.dataSource.data = lineups.filter(item => this.hasLineupToCompile(item))
        this.hasLineupsToCompile = this.dataSource.data.length > 0;
      }
        );
  }

  hasLineupToCompile(match: LineupMatch) {
    return match.reportId !== undefined && match.reportId !== null &&
           (match.teamId === this.authenticationService.getTeamId() || match.otherTeamId === this.authenticationService.getTeamId()) &&
           ((match.teamId === this.authenticationService.getTeamId() && !match.teamLineupSubmitted) ||
           (match.otherTeamId === this.authenticationService.getTeamId() && !match.otherTeamLineupSubmitted));
  }

  ngOnInit(): void {
  }

}
