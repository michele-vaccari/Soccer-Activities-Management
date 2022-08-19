import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Team } from '../interfaces/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {

  updateTeam() {
    if (this.id == null)
      return;
    
    const team: Team = {
      id: this.id,
      name: this.teamForm.controls['name'].value,
      description: this.teamForm.controls['description'].value,
      headquarters: this.teamForm.controls['headquarters'].value,
      sponsorName: this.teamForm.controls['officialSponsor'].value
    };

    this.teamService.updateTeam(this.id, team).subscribe(
      {
        error: (e) => {
          let message = $localize `:@@ERROR_UPDATING_TEAM:Error updating team`;

        if (e.status == 404)
          message = $localize `:@@ERROR_UPDATING_TEAM_TEAM_NOT_FOUND:Error updating team, team not found`;
        },
        complete: () => {
          this.snackBar.open($localize `:@@TEAM_SUCCESSFULLY_UPDATED:Team successfully updated`);
          this.router.navigate(['teams', this.id]);
        }
      }
    );
  }

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
          this.teamForm.controls['name'].setValue(team.name);
          this.teamForm.controls['description'].setValue(team.description);
          this.teamForm.controls['headquarters'].setValue(team.headquarters);
          this.teamForm.controls['officialSponsor'].setValue(team.sponsorName);
        },
        error: () => {
          this.snackBar.open($localize `:@@ERROR_RETRIEVING_TEAM_TEAM_NOT_FOUND:Error retrieving team, team not found`);
          this.router.navigate(['home']);
        }
      }
    );
  }

  ngOnInit(): void {
  }

  areTeamFormValid() {
    return this.teamForm.controls['name'].valid &&
           this.teamForm.controls['description'].valid &&
           this.teamForm.controls['headquarters'].valid &&
           this.teamForm.controls['officialSponsor'].valid;
  }

  teamForm = new FormGroup({
    name: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
    headquarters: new FormControl('', Validators.required),
    officialSponsor: new FormControl('', Validators.required),
  });

  private id?: number;
}
