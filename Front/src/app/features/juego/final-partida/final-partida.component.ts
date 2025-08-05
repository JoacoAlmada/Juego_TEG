import {Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import {NgIf, NgStyle} from '@angular/common';
import {ActivatedRoute, Router, RouterLink, RouterOutlet} from '@angular/router';
import {PartidaService} from '../../../services/Partida.service';
import {Color, JugadorDTO, ObjetivoDTO} from '../../../interfaces/models';

@Component({
  selector: 'app-final-partida',
  imports: [
    NgStyle,
    NgIf,
  ],
  templateUrl: './final-partida.component.html',
  styleUrl: './final-partida.component.css',
  standalone: true,

})
export class FinalPartidaComponent implements OnInit {

  private router = inject(Router);
  private route = inject(ActivatedRoute);

  constructor(private partidaService : PartidaService) {
  }

  @Output() cerrarFinalizado = new EventEmitter<void>();

  nombreGanador = '';

  objetivoGanador?: ObjetivoDTO;

  colorGanador?:Color;

  ngOnInit(): void {
    const partidaId = +this.route.snapshot.paramMap.get('id')!;
    if (partidaId) {
      this.traerGanador(partidaId);
    } else {
      console.error('No se pudo obtener partidaId');
    }
  }

  volverAlMenu() {
    this.cerrarFinalizado.emit();
    this.router.navigate(['/home']);

  }


  traerGanador(partidaId: number): void {
    this.partidaService.getGanador(partidaId).subscribe({
      next: (jugador: JugadorDTO) => {
        this.nombreGanador = jugador.nombre;
        this.objetivoGanador = jugador.objetivo;
        this.colorGanador = jugador.color;
      },
      error: (err) => {
        console.error('Error al traer el jugador ganador:', err);
      }
    });
  }
}
