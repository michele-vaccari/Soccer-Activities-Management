import {Component, OnInit} from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../interfaces/user';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';

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
      columnDef: 'role',
      header: $localize `:@@ROLE:Role`,
      cell: (element: User) => `${element.role}`,
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
      columnDef: 'password',
      header: $localize `:@@PASSWORD:Password`,
      cell: (element: User) => `${element.password}`,
    },
    {
      columnDef: 'Active',
      header: $localize `:@@ACTIVE:Active`,
      cell: (element: User) => `${element.active}`,
    }
  ];
  displayedColumns = this.columns.map(c => c.columnDef).concat(['actions']);
  dataSource = new MatTableDataSource<User>();

  constructor(private userService: UserService,
              private snackBar: MatSnackBar) {
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe((data: User[]) => this.dataSource.data = data);
  }

  deactivateUser(id: number) {
    this.userService.deactivateUser(id).subscribe(
      {
        error: () => {
          this.snackBar.open($localize `:@@ERROR_DEACTIVATING_USER:Error deactivating user`);
        },
        complete: () => {
          this.getAllUsers();
          this.snackBar.open($localize `:@@USER_SUCCESSFULLY_DEACTIVATED:User successfully deactivated`);
        }
      }
    );
  }

  ngOnInit() {
    this.getAllUsers();
  }
}