import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_ENDPOINT } from '../constants';
import { Match } from '../interfaces/match';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  constructor(private http: HttpClient) { }
  
  updateMatch(id: number, match: Match) {
    return this.http.patch<Match>(
      API_ENDPOINT + '/matches/' + id,
      match,
      this.httpOptionsContentTypeJson
    );
  }
  
  private httpOptionsContentTypeJson = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
}
