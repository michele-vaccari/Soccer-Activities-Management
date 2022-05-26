import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleEliminationStageComponent } from './single-elimination-stage.component';

describe('SingleEliminationStageComponent', () => {
  let component: SingleEliminationStageComponent;
  let fixture: ComponentFixture<SingleEliminationStageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleEliminationStageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SingleEliminationStageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
