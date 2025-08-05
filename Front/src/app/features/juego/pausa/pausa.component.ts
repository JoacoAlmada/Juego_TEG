import {Component, EventEmitter, Output} from '@angular/core';
import {Router} from '@angular/router';
import {NgIf} from '@angular/common';
import {ReglasComponent} from '../reglas/reglas.component';


@Component({
  selector: 'app-pausa',
  standalone: true,
  imports: [
  ],
  templateUrl: './Pausa.Component.html',
  styleUrls: ['./Pausa.Component.css'],
})

export class PausaComponent {

  constructor(private router: Router) {
  }

  @Output() cerrarPausa = new EventEmitter<void>();
  @Output() abrirReglas = new EventEmitter<void>();

  reanudarJuego1(): void {
    this.cerrarPausa.emit();
  }

  verReglas(): void {
    this.abrirReglas.emit();
  }

  volverMenu(): void {
    this.router.navigate(['/home']).then(navigated => {
      if (navigated) {
        console.log('Volviendo al Menu Principal...');
      }else {
        console.error('Ocurrio un error de navegacion');
      }
    })
  }

}
