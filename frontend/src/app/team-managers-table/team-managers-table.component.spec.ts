import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamManagersTableComponent } from './team-managers-table.component';

describe('TeamManagersTableComponent', () => {
  let component: TeamManagersTableComponent;
  let fixture: ComponentFixture<TeamManagersTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamManagersTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamManagersTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
