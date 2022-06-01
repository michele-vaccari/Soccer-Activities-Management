import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefereeReportTableComponent } from './referee-report-table.component';

describe('RefereeReportTableComponent', () => {
  let component: RefereeReportTableComponent;
  let fixture: ComponentFixture<RefereeReportTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefereeReportTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefereeReportTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
