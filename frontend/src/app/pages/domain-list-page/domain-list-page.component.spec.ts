import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DomainListPageComponent } from './domain-list-page.component';

describe('DomainListPageComponent', () => {
  let component: DomainListPageComponent;
  let fixture: ComponentFixture<DomainListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DomainListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DomainListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
