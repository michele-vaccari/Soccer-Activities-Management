import { KeyValue } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { TournamentMatch } from '../interfaces/tournament-match';

@Component({
  selector: 'app-round-robin-round',
  templateUrl: './round-robin-round.component.html',
  styleUrls: ['./round-robin-round.component.css']
})
export class RoundRobinRoundComponent implements OnInit {

  @Input() roundDayMatchMap?: Map<number, TournamentMatch[]>;

  constructor() {
   }

  ngOnInit(): void {
  }

  // Order by descending property key
  keyDescOrder = (a: KeyValue<number, TournamentMatch[]>, b: KeyValue<number, TournamentMatch[]>): number => {
    return a.key - b.key;
  }

}
