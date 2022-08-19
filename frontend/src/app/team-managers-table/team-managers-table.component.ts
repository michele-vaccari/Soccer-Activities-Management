import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { User } from '../interfaces/user';
import { TeamManagerService } from '../services/team-manager.service';

@Component({
  selector: 'app-team-managers-table',
  templateUrl: './team-managers-table.component.html',
  styleUrls: ['./team-managers-table.component.css']
})
export class TeamManagersTableComponent implements OnInit {
  columns = [
    {
      columnDef: 'id',
      header: $localize `:@@ID:Id`,
      cell: (element: User) => `${element.id}`,
    },
    {
      columnDef: 'name',
      header: $localize `:@@NAME:Name`,
      cell: (element: User) => `${element.name}`,
    },
    {
      columnDef: 'surname',
      header: $localize `:@@SURNAME:Surname`,
      cell: (element: User) => `${element.surname}`,
    },
    {
      columnDef: 'email',
      header: $localize `:@@EMAIL:Email`,
      cell: (element: User) => `${element.email}`,
    },
    {
      columnDef: 'phone',
      header: $localize `:@@PHONE:Phone`,
      cell: (element: User) => `${element.phone}`,
    },
    {
      columnDef: 'address',
      header: $localize `:@@ADDRESS:Address`,
      cell: (element: User) => `${element.address}`,
    },
    {
      columnDef: 'status',
      header: $localize `:@@STATUS:Status`,
      cell: (element: User) => `${element.active == 'Y' ? $localize `:@@ACTIVE:Active` : $localize `:@@INACTIVE:Inactive`}`,
    },
  ];
  displayedColumns = this.columns.map(c => c.columnDef).concat(['actions']);
  dataSource = new MatTableDataSource<User>();

  constructor(private teamManagerService: TeamManagerService,
              private snackBar: MatSnackBar) {
  }

  deactivateTeamManager(id: number) {
    this.teamManagerService.deactivateTeamManager(id).subscribe(
      {
        error: () => {
          this.snackBar.open($localize `:@@ERROR_DEACTIVATING_TEAM_MANAGER:Error deactivating team manager`);
        },
        complete: () => {
          this.getAllTeamManagers();
          this.snackBar.open($localize `:@@TEAM_MANAGER_SUCCESSFULLY_DEACTIVATED:Team manager successfully deactivated`);
        }
      }
    );
  }

  ngOnInit() {
    this.getAllTeamManagers();
  }

  private getAllTeamManagers() {
    this.teamManagerService.getAllTeamManagers().subscribe((data: User[]) => this.dataSource.data = data);
  }
}
