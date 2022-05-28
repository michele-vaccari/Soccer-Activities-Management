import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  name?: string;
  surname?: string;

  constructor(public authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.name = this.authenticationService.getUserName();
    this.surname = this.authenticationService.getUserSurname();
  }

}
