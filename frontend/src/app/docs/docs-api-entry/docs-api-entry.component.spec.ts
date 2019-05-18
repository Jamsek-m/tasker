import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocsApiEntryComponent } from './docs-api-entry.component';

describe('DocsApiEntryComponent', () => {
  let component: DocsApiEntryComponent;
  let fixture: ComponentFixture<DocsApiEntryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocsApiEntryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocsApiEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
