import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamPlayersTableComponent } from './team-players-table.component';

describe('TeamPlayersTableComponent', () => {
  let component: TeamPlayersTableComponent;
  let fixture: ComponentFixture<TeamPlayersTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamPlayersTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamPlayersTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
