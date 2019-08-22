import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceFormClientSectionComponent } from './service-form-client-section.component';

describe('ServiceFormClientSectionComponent', () => {
  let component: ServiceFormClientSectionComponent;
  let fixture: ComponentFixture<ServiceFormClientSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceFormClientSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceFormClientSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
