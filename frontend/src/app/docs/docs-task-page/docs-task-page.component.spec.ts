import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocsTaskPageComponent } from './docs-task-page.component';

describe('DocsTaskPageComponent', () => {
  let component: DocsTaskPageComponent;
  let fixture: ComponentFixture<DocsTaskPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocsTaskPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocsTaskPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
