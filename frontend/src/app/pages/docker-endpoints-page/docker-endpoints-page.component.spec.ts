import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DockerEndpointsPageComponent } from './docker-endpoints-page.component';

describe('ConfigurationPageComponent', () => {
  let component: DockerEndpointsPageComponent;
  let fixture: ComponentFixture<DockerEndpointsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DockerEndpointsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DockerEndpointsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
