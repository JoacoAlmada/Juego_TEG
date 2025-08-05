import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColocarComponent } from './colocar.component';

describe('colocarEjercitoComponent', () => {
  let component: ColocarComponent;
  let fixture: ComponentFixture<ColocarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColocarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ColocarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
