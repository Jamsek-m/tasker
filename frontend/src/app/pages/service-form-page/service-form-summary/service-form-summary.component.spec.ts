import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceFormSummaryComponent } from './service-form-summary.component';

describe('ServiceFormSummaryComponent', () => {
  let component: ServiceFormSummaryComponent;
  let fixture: ComponentFixture<ServiceFormSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceFormSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceFormSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
