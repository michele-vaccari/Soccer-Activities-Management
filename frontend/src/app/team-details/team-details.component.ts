import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Team } from '../interfaces/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-team-details',
  templateUrl: './team-details.component.html',
  styleUrls: ['./team-details.component.css']
})
export class TeamDetailsComponent implements OnInit {

  team?: Team;

  constructor(private teamService: TeamService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {
      const id = this.route.snapshot.paramMap.get('id');

      if (id == null)
        return;

      this.id = +id;

      this.teamService.getTeam(this.id).subscribe(
        {
          next: (team: Team) => {
            this.team = team;
          },
          error: () => {
            this.snackBar.open($localize `:@@ERROR_RETRIEVING_TEAM_TEAM_NOT_FOUND:Error retrieving team, team not found`);
            this.router.navigate(['teams']);
          }
        }
        );
      }

  ngOnInit(): void {
  }

  private id?: number;
}
