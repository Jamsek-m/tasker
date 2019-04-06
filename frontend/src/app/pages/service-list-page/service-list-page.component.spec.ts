import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceListPageComponent } from './service-list-page.component';

describe('ServiceListPageComponent', () => {
  let component: ServiceListPageComponent;
  let fixture: ComponentFixture<ServiceListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
