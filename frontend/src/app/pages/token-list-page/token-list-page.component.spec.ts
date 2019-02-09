import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TokenListPageComponent } from './token-list-page.component';

describe('TokenListPageComponent', () => {
  let component: TokenListPageComponent;
  let fixture: ComponentFixture<TokenListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TokenListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TokenListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
