import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AtacarComponent } from './atacar.component';

describe('AtacarComponent', () => {
  let component: AtacarComponent;
  let fixture: ComponentFixture<AtacarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AtacarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AtacarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
