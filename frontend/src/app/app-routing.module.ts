import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateTournamentComponent } from './create-tournament/create-tournament.component';
import { HomeComponent } from './home/home.component';
import { LineupComponent } from './lineup/lineup.component';
import { LineupsTableComponent } from './lineups-table/lineups-table.component';
import { LoginComponent } from './login/login.component';
import { PlayerDetailsComponent } from './player-details/player-details.component';
import { PlayerComponent } from './player/player.component';
import { RefereeReportComponent } from './referee-report/referee-report.component';
import { RefereeComponent } from './referee/referee.component';
import { RefereesTableComponent } from './referees-table/referees-table.component';
import { ReportDetailsComponent } from './report-details/report-details.component';
import { ReportTableComponent } from './report-table/report-table.component';
import { ReportComponent } from './report/report.component';
import { TeamDetailsComponent } from './team-details/team-details.component';
import { TeamManagerComponent } from './team-manager/team-manager.component';
import { TeamManagersTableComponent } from './team-managers-table/team-managers-table.component';
import { TeamPlayersTableComponent } from './team-players-table/team-players-table.component';
import { TeamComponent } from './team/team.component';
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
    path: 'team/:id',
    component: TeamComponent
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
    path: 'teams/:id/matches',
    component: LineupsTableComponent
  },
  {
    path: 'players/new',
    component: PlayerComponent
  },
  {
    path: 'players/edit/:id',
    component: PlayerComponent
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
    path: 'tournament',
    component: CreateTournamentComponent
  },
  {
    path: 'report',
    component: ReportComponent
  },
  {
    path: 'reports',
    component: ReportTableComponent
  },
  {
    path: 'reports/:id/lineup',
    component: LineupComponent
  },
  {
    path: 'reports/edit/:id',
    component: RefereeReportComponent
  },
  {
    path: 'reports/view/:id',
    component: ReportDetailsComponent
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
