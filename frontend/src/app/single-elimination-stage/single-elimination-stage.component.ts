import { Component, Input, OnInit } from '@angular/core';
import { TournamentMatch } from '../interfaces/tournament-match';

@Component({
  selector: 'app-single-elimination-stage',
  templateUrl: './single-elimination-stage.component.html',
  styleUrls: ['./single-elimination-stage.component.css']
})
export class SingleEliminationStageComponent implements OnInit {

  @Input() stageName?: string;
  @Input() matchMap?: Map<number, TournamentMatch[]>;

  toBeDefined: string = $localize `:@@TO_BE_DEFINED:To be defined`;

  constructor() { }

  ngOnInit(): void {
  }

}
