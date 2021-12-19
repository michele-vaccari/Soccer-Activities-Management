import { Component, OnInit } from '@angular/core';
import { APPLICATION_NAME } from '../constants';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  applicationName = APPLICATION_NAME;

  constructor() { }

  ngOnInit(): void {
  }

}
