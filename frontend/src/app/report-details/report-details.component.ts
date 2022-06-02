import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayerReport } from '../interfaces/player-report';
import { RefereeReport } from '../interfaces/referee-report';
import { AuthenticationService } from '../services/authentication.service';
import { ReportService } from '../services/report.service';

@Component({
  selector: 'app-report-details',
  templateUrl: './report-details.component.html',
  styleUrls: ['./report-details.component.css']
})
export class ReportDetailsComponent implements OnInit {

  columns = [
    {
      columnDef: 'name',
      header: $localize `:@@PLAYER:Player`,
      cell: (element: PlayerReport) => `${element.jerseyNumber} - ${element.name} ${element.surname}`,
    },
    {
      columnDef: 'goals',
      header: $localize `:@@GOALS:Goals`,
      cell: (element: PlayerReport) => `${(element.goal === undefined || element.goal === 0) ? '' : element.goal}`,
    },
    {
      columnDef: 'cards',
      header: $localize `:@@CARDs:Cards`,
      cell: (element: PlayerReport) => `${(element.admonitions === undefined || element.admonitions === 0) && !element.ejection ? '' : (element.admonitions === 1 ? this.admonition : (element.ejection ? this.expulsion : '' ))}`,
    }
    
  ];
  displayedColumns = this.columns.map(c => c.columnDef);
  reportId: number = 0;
  admonition: string = $localize `:@@ADMONITION:Admonition`;
  expulsion: string = $localize `:@@EXPULSION:expulsion`;

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
    this.reportService.getReport(this.reportId).subscribe(
      {
        next: (refereeReport: RefereeReport) => 
        {
          this.tournamentName = refereeReport.tournamentName;
          this.result = refereeReport.result;

          this.refereeName = refereeReport.refereeName;
          this.refereeSurname = refereeReport.refereeSurname;
          this.matchPlace = refereeReport.matchPlace;
          this.matchDate = refereeReport.matchDate;

          this.teamName = refereeReport.teamName;
          this.teamMainPlayers = refereeReport.mainTeamPlayerReportsDto;
          this.teamReservePlayers = refereeReport.reserveTeamPlayerReportsDto;

          this.otherTeamName = refereeReport.otherTeamName;
          this.otherTeamMainPlayers = refereeReport.mainOtherTeamPlayerReportsDto;
          this.otherTeamReservePlayers = refereeReport.reserveOtherTeamPlayerReportsDto;
          
          this.matchStartTime = refereeReport.matchStartTime;
          this.matchEndTime = refereeReport.matchEndTime;
        },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_REPORT_REPORT_NOT_FOUND:Error retrieving report, report not found`);
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
  result?: string;

  teamName?: string;
  otherTeamName?: string;

  refereeName?: string;
  refereeSurname?: string;
  matchPlace?: string;
  matchDate?: string;
  matchStartTime?: string;
  matchEndTime?: string;

  teamMainPlayers: PlayerReport[] = new Array<PlayerReport>();
  teamReservePlayers: PlayerReport[] = new Array<PlayerReport>();
  otherTeamMainPlayers: PlayerReport[] = new Array<PlayerReport>();
  otherTeamReservePlayers: PlayerReport[] = new Array<PlayerReport>();

  reportForm = new FormGroup({
    goals: new FormControl(''),
    autogoals: new FormControl(''),
    admonitions: new FormControl(''),
    matchStartTime: new FormControl([Validators.required, Validators.pattern("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")]),
    matchEndTime: new FormControl([Validators.required, Validators.pattern("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")])
  });

  ngOnInit(): void {
  }

}
