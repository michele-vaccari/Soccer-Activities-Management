import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINT } from '../constants';
import { Player } from '../interfaces/player';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  constructor(private http: HttpClient) { }

  getPlayer(id: number): Observable<Player> {
    return this.http.get<Player>(
      API_ENDPOINT + '/players/' + id
      );
  }

  addPlayer(player: Player, teamId: number): Observable<Player> {
    return this.http.post<Player>(
      API_ENDPOINT + '/teams/' + teamId + '/players',
      player,
      this.httpOptionsContentTypeJson
    );
  }

  updatePlayer(id: number, player: Player): Observable<Player> {
    return this.http.patch<Player>(
      API_ENDPOINT + '/players/' + id,
      player,
      this.httpOptionsContentTypeJson
    );
  }
  
  private httpOptionsContentTypeJson = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
}
