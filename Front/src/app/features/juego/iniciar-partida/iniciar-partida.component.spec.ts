import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IniciarPartidaComponent } from './iniciar-partida.component';

describe('IniciarPartidaComponent', () => {
  let component: IniciarPartidaComponent;
  let fixture: ComponentFixture<IniciarPartidaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IniciarPartidaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IniciarPartidaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
