import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefereeReportComponent } from './referee-report.component';

describe('RefereeReportComponent', () => {
  let component: RefereeReportComponent;
  let fixture: ComponentFixture<RefereeReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefereeReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefereeReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
