import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LineupsTableComponent } from './lineups-table.component';

describe('LineupsTableComponent', () => {
  let component: LineupsTableComponent;
  let fixture: ComponentFixture<LineupsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LineupsTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LineupsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
