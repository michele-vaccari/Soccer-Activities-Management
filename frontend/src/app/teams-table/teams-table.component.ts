import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Team } from '../interfaces/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-teams-table',
  templateUrl: './teams-table.component.html',
  styleUrls: ['./teams-table.component.css']
})
export class TeamsTableComponent implements OnInit {
  columns = [
    {
      columnDef: 'name',
      header: $localize `:@@NAME:Name`,
      cell: (element: Team) => `${element.name}`,
    },
    {
      columnDef: 'headquarters',
      header: $localize `:@@HEADQUARTERS:Headquarters`,
      cell: (element: Team) => `${element.headquarters}`,
    }
  ];
  displayedColumns = this.columns.map(c => c.columnDef).concat(['actions']);
  dataSource = new MatTableDataSource<Team>();


  constructor(private teamService: TeamService,
              private snackBar: MatSnackBar) {
  }

  getAllTeams() {
    this.teamService.getAllTeams().subscribe((data: Team[]) => this.dataSource.data = data);
  }

  ngOnInit(): void {
    this.getAllTeams();
  }

}
