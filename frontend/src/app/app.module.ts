import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { ToolbarComponent } from './toolbar/toolbar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { UsersTableComponent } from './users-table/users-table.component';
import { MatTableModule } from '@angular/material/table';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { UserComponent } from './user/user.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input'
import { MatSelectModule } from '@angular/material/select';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule, MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material/snack-bar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { LoginComponent } from './login/login.component';
import { MatCardModule } from '@angular/material/card';
import { AuthenticationHtppInterceptorService } from './services/authentication-htpp-interceptor.service';
import { HomeComponent } from './home/home.component';
import { MatListModule } from '@angular/material/list';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TournamentsTableComponent } from './tournaments-table/tournaments-table.component';
import { TournamentComponent } from './tournament/tournament.component';
import { RoundRobinRoundComponent } from './round-robin-round/round-robin-round.component';
import { SingleEliminationStageComponent } from './single-elimination-stage/single-elimination-stage.component';
import { TeamsTableComponent } from './teams-table/teams-table.component';
import { TeamDetailsComponent } from './team-details/team-details.component';
import { TeamPlayersTableComponent } from './team-players-table/team-players-table.component';
import { PlayerDetailsComponent } from './player-details/player-details.component';
import {MatMenuModule} from '@angular/material/menu';
import { ReportTableComponent } from './report-table/report-table.component';
import { RefereesTableComponent } from './referees-table/referees-table.component';
import { RefereeComponent } from './referee/referee.component';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    UsersTableComponent,
    UserComponent,
    LoginComponent,
    HomeComponent,
    TournamentsTableComponent,
    TournamentComponent,
    RoundRobinRoundComponent,
    SingleEliminationStageComponent,
    TeamsTableComponent,
    TeamDetailsComponent,
    TeamPlayersTableComponent,
    PlayerDetailsComponent,
    ReportTableComponent,
    RefereesTableComponent,
    RefereeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatTableModule,
    HttpClientModule,
    FlexLayoutModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatSnackBarModule,
    MatSidenavModule,
    MatCardModule,
    MatListModule,
    MatTooltipModule,
    MatMenuModule
  ],
  providers: [
    {
      provide: MAT_SNACK_BAR_DEFAULT_OPTIONS,
      useValue: {
        duration: 2500,
        horizontalPosition: 'end',
        verticalPosition: 'bottom',
      }
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationHtppInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
