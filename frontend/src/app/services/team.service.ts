import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { Team } from '../interfaces/team';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor(private http: HttpClient) { }

  getAllTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(
      API_ENDPOINT + '/teams'
      );
  }
}
