import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocsPluginPageComponent } from './docs-plugin-page.component';

describe('DocsPluginPageComponent', () => {
  let component: DocsPluginPageComponent;
  let fixture: ComponentFixture<DocsPluginPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocsPluginPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocsPluginPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
