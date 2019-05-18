import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceFormPageComponent } from './service-form-page.component';

describe('ServiceFormPageComponent', () => {
  let component: ServiceFormPageComponent;
  let fixture: ComponentFixture<ServiceFormPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceFormPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceFormPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
