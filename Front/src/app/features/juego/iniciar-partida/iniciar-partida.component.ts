import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { SesionService } from '../../../services/sesion.service';
import {
  agregarBotDTO,
  Color,
  DificultadBot,
  CrearJugadorDTO,
  JugadorPartidaDTO,
  TipoJugador
} from '../../../interfaces/models';
import {IniciarService} from '../../../services/iniciar.service';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'app-iniciar-partida',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './iniciar-partida.component.html',
  styleUrl: './iniciar-partida.component.css'
})
export class IniciarPartidaComponent {
  cantidadJugadores: number = 3;
  partidaId!: number;

  jugadores: { tipo: TipoJugador; dificultad: DificultadBot; nombre: string; color: Color }[] = [];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private sesionService: SesionService,
    private iniciarService: IniciarService
  ) {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.partidaId = +id;
        this.actualizarJugadores();
      }
    });
  }

  coloresDisponibles: Color[] = Object.values(Color) as Color[];

  actualizarJugadores() {
    const nombreUsuario = this.sesionService.getNombreUsuario();
    const coloresDisponibles = [...this.coloresDisponibles];
    const coloresAsignados = new Set<Color>();

    this.jugadores = Array.from({ length: this.cantidadJugadores }, (_, i) => {
      let tipo: TipoJugador = this.jugadores[i]?.tipo || TipoJugador.HUMANO;

      let color: Color = this.jugadores[i]?.color as Color;
      if (!color || coloresAsignados.has(color)) {
        color = coloresDisponibles.find(c => !coloresAsignados.has(c))!;
      }
      coloresAsignados.add(color);

      let nombre = this.jugadores[i]?.nombre;
      if (!nombre) {
        if (i === 0) {
          nombre = nombreUsuario;
        } else {
          nombre = '';
        }
      }

      let dificultad: DificultadBot = this.jugadores[i]?.dificultad || DificultadBot.INTERMEDIO;

      return { tipo, nombre, dificultad, color };
    });
  }


  onTipoChange(jugador: any) {
    if (jugador.tipo === TipoJugador.BOT) {
      jugador.dificultad = DificultadBot.INTERMEDIO;
    } else {
      jugador.dificultad = '';
      jugador.nombre = '';
    }
  }


  onColorChange(index: number) {
    const coloresOcupados = this.jugadores.map(j => j.color);
    const coloresLibres = this.coloresDisponibles.filter(c => !coloresOcupados.includes(c));
    for (let i = 0; i < this.jugadores.length; i++) {
      if (i !== index && this.jugadores[i].color === this.jugadores[index].color) {
        this.jugadores[i].color = coloresLibres[0];
      }
    }
  }


  esColorOcupado(color: Color, indexActual: number): boolean {
    return this.jugadores.some((j, idx) => idx !== indexActual && j.color === color);
  }

  esColorClaro(color: Color): boolean {
    return [Color.AMARILLO, Color.MAGENTA, Color.VERDE].includes(color);
  }

  obtenerColorFondo(color: Color): string {
    const colores = {
      VERDE: '#36a300',
      ROJO: '#cf0000',
      NEGRO: '#000000',
      AZUL: '#001bfc',
      AMARILLO: '#f4fc00',
      MAGENTA: '#f100c9'
    };
    return colores[color] || '#132245';
  }

  esFormularioValido(): boolean {
    return this.jugadores.every(j =>
      (j.tipo === TipoJugador.HUMANO && j.nombre.trim() !== '') ||
      (j.tipo === TipoJugador.BOT && j.dificultad)
    );
  }

  iniciarPartida() {
    if (!this.esFormularioValido()) return;
    const idPartida = this.partidaId

    const humano = this.jugadores.filter(j => j.tipo === TipoJugador.HUMANO);
    const humanos: CrearJugadorDTO[] = humano.map(j => ({
      nombre: j.nombre,
      color: j.color,
    }));
    const jugadorPartidaDTO: JugadorPartidaDTO = {
      jugadores: humanos,
      partidaId: idPartida
    };

    const bots = this.jugadores.filter(j => j.tipo === TipoJugador.BOT);
    const botNovato = bots.filter(b => b.dificultad === DificultadBot.NOVATO);
    const coloresNovato: Color[] = [];
    botNovato.forEach(b => {
      coloresNovato.push(b.color);
    });
    const botIntermedio = bots.filter(b => b.dificultad === DificultadBot.INTERMEDIO);
    const coloresIntermedio: Color[] = [];
    botIntermedio.forEach(b => {
      coloresIntermedio.push(b.color);
    })
    const botExperto = bots.filter(b => b.dificultad === DificultadBot.EXPERTO);
    const coloresExperto: Color[] = [];
    botExperto.forEach(b => {
      coloresExperto.push(b.color);
    })

    const novatos: agregarBotDTO = {
      dificultad: DificultadBot.NOVATO,
      cantidad: botNovato.length,
      colores: coloresNovato
    }
    const intermedios: agregarBotDTO = {
      dificultad: DificultadBot.INTERMEDIO,
      cantidad: botIntermedio.length,
      colores: coloresIntermedio
    }
    const expertos: agregarBotDTO = {
      dificultad: DificultadBot.EXPERTO,
      cantidad: botExperto.length,
      colores: coloresExperto
    }

    this.iniciarService.crearJugadorEnPartida(jugadorPartidaDTO).subscribe({
      next: () => {
        forkJoin([
          this.iniciarService.agregarBots(idPartida, novatos),
          this.iniciarService.agregarBots(idPartida, intermedios),
          this.iniciarService.agregarBots(idPartida, expertos)
        ]).subscribe({
          next: () => {
            this.iniciarService.iniciarPartida(idPartida).subscribe({
              next: () => {
                this.router.navigate(['/juego/colocar/' + idPartida]);
              },
              error: err => {
                console.error('Error al iniciar la partida:', err);
              }
            });
          },
          error: err => {
            console.error('Error al agregar bots:', err);
          }
        });
      },
      error: err => {
        console.error('Error al crear jugadores en la jugadorPartidaDTO:', err);
      }
    });
  }

  volverInicio() {
    this.router.navigate(['/home'])
  }

  protected readonly TipoJugador = TipoJugador;
  protected readonly DificultadBot = DificultadBot;
}
