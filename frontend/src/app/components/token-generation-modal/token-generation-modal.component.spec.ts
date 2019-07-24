import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TokenGenerationModalComponent } from './token-generation-modal.component';

describe('TokenGenerationModalComponent', () => {
  let component: TokenGenerationModalComponent;
  let fixture: ComponentFixture<TokenGenerationModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TokenGenerationModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TokenGenerationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
