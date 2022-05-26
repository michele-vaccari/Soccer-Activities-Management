import { Component, OnInit } from '@angular/core';
import { TournamentService } from '../services/tournament.service';
import { ShortTournament } from '../interfaces/short-tournament';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-tournaments-table',
  templateUrl: './tournaments-table.component.html',
  styleUrls: ['./tournaments-table.component.css']
})
export class TournamentsTableComponent implements OnInit {

  columns = [
    {
      columnDef: 'name',
      header: $localize `:@@TOURNAMENT_NAME:Tournament name`,
      cell: (element: ShortTournament) => `${element.name}`,
    },
    {
      columnDef: 'type',
      header: $localize `:@@TYPE:Type`,
      cell: (element: ShortTournament) => `${element.type == "RoundRobin" ? $localize `:@@ROUND_ROBIN:Round Robin` : $localize `:@@SINGLE_ELIMINATION:Single Elimination`}`,
    }
  ];
  displayedColumns = this.columns.map(c => c.columnDef).concat(['actions']);
  dataSource = new MatTableDataSource<ShortTournament>();

  constructor(private tournamentService: TournamentService) {
  }

  getAllUsers() {
    this.tournamentService.getAllTournaments().subscribe((data: ShortTournament[]) => this.dataSource.data = data);
  }

  ngOnInit() {
    this.getAllUsers();
  }

}
