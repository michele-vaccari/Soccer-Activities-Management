import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoundRobinRoundComponent } from './round-robin-round.component';

describe('RoundRobinRoundComponent', () => {
  let component: RoundRobinRoundComponent;
  let fixture: ComponentFixture<RoundRobinRoundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoundRobinRoundComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoundRobinRoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
