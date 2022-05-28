import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { Player } from '../interfaces/player';
import { ShortTournament } from '../interfaces/short-tournament';
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

  getTeam(id: number): Observable<Team> {
    return this.http.get<Team>(
      API_ENDPOINT + '/teams/' + id
      );
  }

  getAllPlayersOfTeam(id: number): Observable<Player[]> {
    return this.http.get<Player[]>(
      API_ENDPOINT + '/teams/' + id + '/players'
      );
  }

  getAllTournamentsOfTeam(id: number): Observable<ShortTournament[]> {
    return this.http.get<ShortTournament[]>(
      API_ENDPOINT + '/teams/' + id + '/tournaments'
      );
  }
}
