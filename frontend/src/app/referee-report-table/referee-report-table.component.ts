import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Report } from '../interfaces/report';
import { AuthenticationService } from '../services/authentication.service';
import { ReportService } from '../services/report.service';

@Component({
  selector: 'app-referee-report-table',
  templateUrl: './referee-report-table.component.html',
  styleUrls: ['./referee-report-table.component.css']
})
export class RefereeReportTableComponent implements OnInit {

  columns = [
    {
      columnDef: 'tournamentName',
      header: $localize `:@@TOURNAMENT_NAME:Tournament name`,
      cell: (element: Report) => `${element.tournamentName}`,
    },
    {
      columnDef: 'match',
      header: $localize `:@@MATCH:Match`,
      cell: (element: Report) => `${element.teamName} - ${element.otherTeamName}`,
    },
    {
      columnDef: 'date',
      header: $localize `:@@DATE:Date`,
      cell: (element: Report) => `${element.matchDate}`,
    },
    {
      columnDef: 'result',
      header: $localize `:@@RESULT:Result`,
      cell: (element: Report) => `${element.result === null || element.result === undefined ?  $localize `:@@TO_BE_DEFINED:To be defined` : element.result}`,
    },
    {
      columnDef: 'info',
      header: $localize `:@@INFO:Info`,
      cell: (element: Report) => `${element.compiled ? $localize `:@@COMPILED:Compiled` : $localize `:@@NOT_COMPILED:Not compiled`}`,
    },
  ];
  displayedColumns = this.columns.map(c => c.columnDef).concat(['actions']);
  dataSource = new MatTableDataSource<Report>();

  constructor(private reportService: ReportService,
              private authenticationService: AuthenticationService) {
  }

  getAllReports() {
    this.reportService.getReportsOfReferee(this.authenticationService.getUserId()).subscribe((data: Report[]) => this.dataSource.data = data);
  }

  ngOnInit() {
    this.getAllReports();
  }

}
