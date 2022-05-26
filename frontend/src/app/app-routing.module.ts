import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { TeamDetailsComponent } from './team-details/team-details.component';
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
    path: 'teams',
    component: TeamsTableComponent
  },
  {
    path: 'teams/:id',
    component: TeamDetailsComponent
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
