import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceEditPageComponent } from './service-edit-page.component';

describe('ServiceEditPageComponent', () => {
  let component: ServiceEditPageComponent;
  let fixture: ComponentFixture<ServiceEditPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceEditPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
