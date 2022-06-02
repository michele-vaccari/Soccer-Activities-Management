import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { Lineup } from '../interfaces/lineup';
import { RefereeReport } from '../interfaces/referee-report';
import { Report } from '../interfaces/report';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) { }

  getAllReports(): Observable<Report[]> {
    return this.http.get<Report[]>(
      API_ENDPOINT + '/reports'
      );
  }

  getReport(reportId: number): Observable<RefereeReport> {
    return this.http.get<RefereeReport>(
      API_ENDPOINT + '/reports/' + reportId
      );
  }

  getReportsOfReferee(refereeId: number): Observable<Report[]> {
    return this.http.get<Report[]>(
      API_ENDPOINT + '/referees/' + refereeId + '/reports'
      );
  }

  getLineups(reportId: number): Observable<RefereeReport> {
    return this.http.get<RefereeReport>(
      API_ENDPOINT + '/reports/' + reportId + '/lineups'
      );
  }

  addLineup(reportId: number, lineup: Lineup): Observable<Lineup> {
    return this.http.post<Lineup>(
      API_ENDPOINT + '/reports/' + reportId + '/lineups',
      lineup,
      this.httpOptionsContentTypeJson
    );
  }

  patchReport(reportId: number, refereeReport: RefereeReport): Observable<RefereeReport> {
    return this.http.patch<RefereeReport>(
      API_ENDPOINT + '/reports/' + reportId,
      refereeReport,
      this.httpOptionsContentTypeJson
    );
  }

  private httpOptionsContentTypeJson = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
}
