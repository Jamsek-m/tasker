import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceAddPageComponent } from './service-add-page.component';

describe('ServiceAddPageComponent', () => {
  let component: ServiceAddPageComponent;
  let fixture: ComponentFixture<ServiceAddPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceAddPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceAddPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
