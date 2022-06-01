import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { Lineup } from '../interfaces/lineup';
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

  getReportsOfReferee(refereeId: number): Observable<Report[]> {
    return this.http.get<Report[]>(
      API_ENDPOINT + '/referees/' + refereeId + '/reports'
      );
  }

  addLineup(reportId: number, lineup: Lineup): Observable<Lineup> {
    return this.http.post<Lineup>(
      API_ENDPOINT + '/reports/' + reportId + '/lineups',
      lineup,
      this.httpOptionsContentTypeJson
    );
  }

  private httpOptionsContentTypeJson = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
}
