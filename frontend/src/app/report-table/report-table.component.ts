import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Report } from '../interfaces/report';
import { ReportService } from '../services/report.service';

@Component({
  selector: 'app-report-table',
  templateUrl: './report-table.component.html',
  styleUrls: ['./report-table.component.css']
})
export class ReportTableComponent implements OnInit {
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
      columnDef: 'place',
      header: $localize `:@@PLACE:Place`,
      cell: (element: Report) => `${element.place}`,
    },
    {
      columnDef: 'result',
      header: $localize `:@@RESULT:Result`,
      cell: (element: Report) => `${element.result === null || element.result === undefined ?  $localize `:@@TO_BE_DEFINED:To be defined` : element.result}`,
    },
    {
      columnDef: 'referee',
      header: $localize `:@@REFEREE:Referee`,
      cell: (element: Report) => `${element.refereeName} ${element.refereeSurname}`,
    },
    {
      columnDef: 'info',
      header: $localize `:@@INFO:Info`,
      cell: (element: Report) => `${element.compiled ? $localize `:@@COMPILED:Compiled` : $localize `:@@NOT_COMPILED:Not compiled`}`,
    },
  ];
  displayedColumns = this.columns.map(c => c.columnDef);
  dataSource = new MatTableDataSource<Report>();

  constructor(private reportService: ReportService) {
  }

  getAllReports() {
    this.reportService.getAllReports().subscribe((data: Report[]) => this.dataSource.data = data);
  }

  ngOnInit() {
    this.getAllReports();
  }

}
