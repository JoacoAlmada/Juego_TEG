import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PantallaJuegoComponent } from './pantalla-juego.component';

describe('PantallaJuegoComponent', () => {
  let component: PantallaJuegoComponent;
  let fixture: ComponentFixture<PantallaJuegoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PantallaJuegoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PantallaJuegoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
