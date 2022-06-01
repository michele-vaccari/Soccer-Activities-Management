import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { LineupMatch } from '../interfaces/lineup-match';
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

  getAllLineupsOfTeam(id: number): Observable<LineupMatch[]> {
    return this.http.get<LineupMatch[]>(
      API_ENDPOINT + '/teams/' + id + '/matches'
      );
  }

  updateTeam(id: number, team: Team): Observable<Team> {
    return this.http.patch<Team>(
      API_ENDPOINT + '/teams/' + id,
      team,
      this.httpOptionsContentTypeJson
      );
    }

  deactivatePlayer(id: number) {
    return this.http.delete(
      API_ENDPOINT + '/players/' + id
    );
  }

  private httpOptionsContentTypeJson = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
}
