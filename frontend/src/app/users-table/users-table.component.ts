import {Component} from '@angular/core';

export interface User {
  id: number,
  type: string,
  name: string;
  surname: string;
  email: string;
  password: string;
  isActive: string;
}

const ELEMENT_DATA: User[] = [
  { id: 1, type: "SystemAdministrator", name: "Michele", surname: "Vaccari", email: "m.vaccari@sam.com", password: "Password01", isActive: "Y" }
];

@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.css']
})
export class UsersTableComponent {
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
    },
  ];
  dataSource = ELEMENT_DATA;
  displayedColumns = this.columns.map(c => c.columnDef);
}