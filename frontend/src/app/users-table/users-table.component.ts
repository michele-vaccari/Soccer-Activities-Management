import {Component, OnInit} from '@angular/core';
import { User } from '../interfaces/user';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RefereeService } from '../services/referee.service';

@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.css']
})
export class UsersTableComponent implements OnInit {
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

  constructor(private refereeService: RefereeService,
              private snackBar: MatSnackBar) {
  }

  deactivateReferee(id: number) {
    this.refereeService.deactivateReferee(id).subscribe(
      {
        error: () => {
          this.snackBar.open($localize `:@@ERROR_DEACTIVATING_REFEREE:Error deactivating referee`);
        },
        complete: () => {
          this.getAllReferees();
          this.snackBar.open($localize `:@@REFEREE_SUCCESSFULLY_DEACTIVATED:Referee successfully deactivated`);
        }
      }
    );
  }

  ngOnInit() {
    this.getAllReferees();
  }

  private getAllReferees() {
    this.refereeService.getAllReferees().subscribe((data: User[]) => this.dataSource.data = data);
  }
}