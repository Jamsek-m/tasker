import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerListPageComponent } from './server-list-page.component';

describe('ServerListPageComponent', () => {
  let component: ServerListPageComponent;
  let fixture: ComponentFixture<ServerListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServerListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
