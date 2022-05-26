import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ShortTournament } from '../interfaces/short-tournament';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { Tournament } from '../interfaces/tournament';

@Injectable({
  providedIn: 'root'
})
export class TournamentService {

  constructor(private http: HttpClient) { }

  getAllTournaments(): Observable<ShortTournament[]> {
    return this.http.get<ShortTournament[]>(
      API_ENDPOINT + '/tournaments'
      );
  }

  getTournament(id: number): Observable<Tournament> {
    return this.http.get<Tournament>(
      API_ENDPOINT + '/tournaments/' + id
      );
  }

}
