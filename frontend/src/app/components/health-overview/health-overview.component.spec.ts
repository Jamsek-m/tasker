import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthOverviewComponent } from './health-overview.component';

describe('HealthOverviewComponent', () => {
  let component: HealthOverviewComponent;
  let fixture: ComponentFixture<HealthOverviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HealthOverviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
