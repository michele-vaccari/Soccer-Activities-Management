import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { Report } from '../interfaces/report';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<Report[]> {
    return this.http.get<Report[]>(
      API_ENDPOINT + '/reports'
      );
  }
}
