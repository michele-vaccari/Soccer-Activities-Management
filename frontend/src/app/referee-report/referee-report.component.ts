import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayerReport } from '../interfaces/player-report';
import { RefereeReport } from '../interfaces/referee-report';
import { AuthenticationService } from '../services/authentication.service';
import { ReportService } from '../services/report.service';

@Component({
  selector: 'app-referee-report',
  templateUrl: './referee-report.component.html',
  styleUrls: ['./referee-report.component.css']
})
export class RefereeReportComponent implements OnInit {

  columns = [
    {
      columnDef: 'name',
      header: $localize `:@@PLAYER:Player`,
      cell: (element: PlayerReport) => `${element.jerseyNumber} - ${element.name} ${element.surname}`,
    }
  ];
  displayedColumns = this.columns.map(c => c.columnDef).concat(['playerReport']);
  reportId: number = 0;

  patchReport() {
    let autogoalTeam = 0;
    this.teamMainPlayers.forEach(player => {
      if (player.autogoals !== undefined)
        autogoalTeam += player.autogoals;
    });
    this.teamReservePlayers.forEach(player => {
      if (player.autogoals !== undefined)
        autogoalTeam += player.autogoals;
    });

    let autogoalOtherTeam = 0;
    this.otherTeamMainPlayers.forEach(player => {
      if (player.autogoals !== undefined)
        autogoalOtherTeam += player.autogoals;
    });
    this.otherTeamReservePlayers.forEach(player => {
      if (player.autogoals !== undefined)
        autogoalOtherTeam += player.autogoals;
    });

    const refereeReport: RefereeReport = {
      mainTeamPlayerReportsDto: this.teamMainPlayers.map(({autogoals, ...keepAttrs}) => keepAttrs),
      reserveTeamPlayerReportsDto: this.teamReservePlayers.map(({autogoals, ...keepAttrs}) => keepAttrs),
      mainOtherTeamPlayerReportsDto: this.otherTeamMainPlayers.map(({autogoals, ...keepAttrs}) => keepAttrs),
      reserveOtherTeamPlayerReportsDto: this.otherTeamReservePlayers.map(({autogoals, ...keepAttrs}) => keepAttrs),
      autogoalTeam: autogoalTeam,
      autogoalOtherTeam: autogoalOtherTeam,
      matchStartTime: this.reportForm.controls['matchStartTime'].value,
      matchEndTime: this.reportForm.controls['matchEndTime'].value
    };

    this.reportService.patchReport(this.reportId, refereeReport).subscribe(
      {
        error: (e) => {
          let message = $localize `:@@ERROR_UPDATING_REPORT:Error updating report`;

          if (e.status == 404)
            message = $localize `:@@ERROR_UPDATING_REPORT_REPORT_NOT_FOUND:Error updating lineup, report not found`;
          
          this.snackBar.open(message);
        },
        complete: () => {
          this.snackBar.open($localize `:@@REPORT_SUCCESSFULLY_UPDATED:Report successfully updated`);
          this.router.navigate(['/home']);
        }
      }
    );
  }

  constructor(public authenticationService: AuthenticationService,
              private reportService: ReportService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {

    const reportId = this.route.snapshot.paramMap.get('id');

    if (reportId == null)
      return;

    this.reportId = parseInt(reportId);

    this.getLineups()
  }

  getLineups() {
    this.reportService.getLineups(this.reportId).subscribe(
      {
        next: (refereeReport: RefereeReport) => 
        {
          this.tournamentName = refereeReport.tournamentName;

          this.teamName = refereeReport.teamName;
          this.teamMainPlayers = refereeReport.mainTeamPlayerReportsDto;
          this.teamReservePlayers = refereeReport.reserveTeamPlayerReportsDto;

          this.otherTeamName = refereeReport.otherTeamName;
          this.otherTeamMainPlayers = refereeReport.mainOtherTeamPlayerReportsDto;
          this.otherTeamReservePlayers = refereeReport.reserveOtherTeamPlayerReportsDto;
        },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_LINEUPS_OF_REPORT_REPORT_NOT_FOUND:Error retrieving lineups of report, report not found`);
          this.router.navigate(['home']);
        }
      }
    );
  }

  canSubmitReport() {
    return this.reportForm.controls['matchStartTime'].valid &&
           this.reportForm.controls['matchEndTime'].valid;
  }

  tournamentName?: string;
  teamName?: string;
  otherTeamName?: string;

  teamMainPlayers: PlayerReport[] = new Array<PlayerReport>();
  teamReservePlayers: PlayerReport[] = new Array<PlayerReport>();
  otherTeamMainPlayers: PlayerReport[] = new Array<PlayerReport>();
  otherTeamReservePlayers: PlayerReport[] = new Array<PlayerReport>();

  reportForm = new FormGroup({
    goal: new FormControl(''),
    autogoals: new FormControl(''),
    admonitions: new FormControl(''),
    matchStartTime: new FormControl('', [Validators.required, Validators.pattern("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")]),
    matchEndTime: new FormControl('', [Validators.required, Validators.pattern("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")])
  });

  ngOnInit(): void {
  }

}
