import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Player } from '../interfaces/player';
import { PlayerService } from '../services/player.service';
import { TeamService } from '../services/team.service';
import { Team } from '../interfaces/team';

@Component({
  selector: 'app-player-details',
  templateUrl: './player-details.component.html',
  styleUrls: ['./player-details.component.css']
})
export class PlayerDetailsComponent implements OnInit {

  player?: Player;
  teamName?: string;

  constructor(private playerService: PlayerService,
              private teamService: TeamService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {
  
    const id = this.route.snapshot.paramMap.get('id');

    if (id == null)
      return;

    this.playerService.getPlayer(+id).subscribe(
      {
        next: (player: Player) => {
          this.player = player;
          this.getTeamName();
        },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_PLAYER_PLAYER_NOT_FOUND:Error retrieving player, player not found`);
          this.router.navigate(['teams']);
        }
      }
    );
  }

  getTeamName() {
    if (this.player == null)
      return;

    this.teamService.getTeam(this.player.teamId).subscribe(
      {
        next: (team: Team) => {
          this.teamName = team.name; },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_TEAM_TEAM_NOT_FOUND:Error retrieving team, team not found`);
          this.router.navigate(['teams']);
        }
      }
    );
  }

  ngOnInit(): void {
  }

}
