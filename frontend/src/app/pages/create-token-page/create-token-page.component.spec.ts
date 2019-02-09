import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTokenPageComponent } from './create-token-page.component';

describe('CreateTokenPageComponent', () => {
  let component: CreateTokenPageComponent;
  let fixture: ComponentFixture<CreateTokenPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateTokenPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTokenPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
