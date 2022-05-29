import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefereesTableComponent } from './referees-table.component';

describe('RefereesTableComponent', () => {
  let component: RefereesTableComponent;
  let fixture: ComponentFixture<RefereesTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefereesTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefereesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
