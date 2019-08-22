import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceFormApiSectionComponent } from './service-form-api-section.component';

describe('ServiceFormApiSectionComponent', () => {
  let component: ServiceFormApiSectionComponent;
  let fixture: ComponentFixture<ServiceFormApiSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceFormApiSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceFormApiSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
