import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginationLimitComponent } from './pagination-limit.component';

describe('PaginationLimitComponent', () => {
  let component: PaginationLimitComponent;
  let fixture: ComponentFixture<PaginationLimitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaginationLimitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaginationLimitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
