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
      header: 'Id',
      cell: (element: User) => `${element.id}`,
    },
    {
      columnDef: 'type',
      header: 'Type',
      cell: (element: User) => `${element.type}`,
    },
    {
      columnDef: 'name',
      header: 'Name',
      cell: (element: User) => `${element.name}`,
    },
    {
      columnDef: 'surname',
      header: 'Surname',
      cell: (element: User) => `${element.surname}`,
    },
    {
      columnDef: 'email',
      header: 'Email',
      cell: (element: User) => `${element.email}`,
    },
    {
      columnDef: 'password',
      header: 'Password',
      cell: (element: User) => `${element.password}`,
    },
    {
      columnDef: 'isActive',
      header: 'Is Active',
      cell: (element: User) => `${element.isActive}`,
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
          this.snackBar.open('Error deactivating user');
        },
        complete: () => {
          this.getAllUsers();
          this.snackBar.open(`User successfully deactivated`);
        }
      }
    );
  }

  ngOnInit() {
    this.getAllUsers();
  }
}