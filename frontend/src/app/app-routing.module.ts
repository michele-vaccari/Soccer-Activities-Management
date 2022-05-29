import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { PlayerDetailsComponent } from './player-details/player-details.component';
import { RefereeComponent } from './referee/referee.component';
import { RefereesTableComponent } from './referees-table/referees-table.component';
import { ReportTableComponent } from './report-table/report-table.component';
import { TeamDetailsComponent } from './team-details/team-details.component';
import { TeamManagerComponent } from './team-manager/team-manager.component';
import { TeamManagersTableComponent } from './team-managers-table/team-managers-table.component';
import { TeamPlayersTableComponent } from './team-players-table/team-players-table.component';
import { TeamsTableComponent } from './teams-table/teams-table.component';
import { TournamentComponent } from './tournament/tournament.component';
import { TournamentsTableComponent } from './tournaments-table/tournaments-table.component';
import { UserComponent } from './user/user.component';
import { UsersTableComponent } from './users-table/users-table.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'admins/:id',
    component: UserComponent
  },
  {
    path: 'teams',
    component: TeamsTableComponent
  },
  {
    path: 'teams/:id',
    component: TeamDetailsComponent
  },
  {
    path: 'teams/:id/players',
    component: TeamPlayersTableComponent
  },
  {
    path: 'teams/:id/tournaments',
    component: TournamentsTableComponent
  },
  {
    path: 'players/:id',
    component: PlayerDetailsComponent
  },
  {
    path: 'tournaments',
    component: TournamentsTableComponent
  },
  {
    path: 'tournaments/:id',
    component: TournamentComponent
  },
  {
    path: 'reports',
    component: ReportTableComponent
  },
  {
    path: 'teammanagers',
    component: TeamManagersTableComponent
  },
  {
    path: 'teammanager',
    component: TeamManagerComponent
  },
  {
    path: 'teammanagers/:id',
    component: TeamManagerComponent
  },
  {
    path: 'referees',
    component: RefereesTableComponent
  },
  {
    path: 'referee',
    component: RefereeComponent
  },
  {
    path: 'referees/:id',
    component: RefereeComponent
  },
  {
    path: 'users',
    component: UsersTableComponent
  },
  {
    path: 'user',
    component: UserComponent
  },
  {
    path: 'users/:id',
    component: UserComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
