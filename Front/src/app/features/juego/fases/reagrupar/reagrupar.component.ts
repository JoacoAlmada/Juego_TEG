import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {NgClass, NgForOf, NgIf, NgOptimizedImage} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {Carta, Color, EstadoPartida, JugadorDTO, ObjetivoDTO, Pais, PaisDTO} from '../../../../interfaces/models';
import {PartidaService} from '../../../../services/Partida.service';
import {JuegoService} from '../../../../services/juego.service';
import {Subscription} from 'rxjs';
import {SeleccionPaisService} from '../../../../services/SeleccionarPaisService';
import {BotService} from '../../../../services/bot.service';
import {PaisService} from '../../../../services/pais.service';
import {PantallaJuegoComponent} from '../../pantalla-juego/pantalla-juego.component';

@Component({
  selector: 'app-reagrupar',
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    NgOptimizedImage,
    NgClass
  ],
  templateUrl: './reagrupar.component.html',
  styleUrl: './reagrupar.component.css'
})
export class ReagruparComponent implements OnInit {
  jugadores: JugadorDTO[] = [];
  partidaId!: number;
  paises: Pais[] = [];
  partida: Subscription | undefined;
  opcionesEjercitos: number[] = [];
  paisesJugador: PaisDTO[] = [];
  paisesVecinos: PaisDTO[] = [];
  objetivoActual: ObjetivoDTO | undefined;

  @ViewChild('dialogoCartas', { static: false })
  dialogoCartas!: ElementRef<HTMLDialogElement>;

  mostrarObjetivo = false;
  mostrarCartas = false;
  cantidadEjercitos = 1;
  ejercitosDisponibles = 1;
  paisSeleccionado: any = null;
  vecinoSeleccionado: any = null;
  isEjecutandoBot: boolean = false;
  turnoJugador: string | undefined;
  colorJugador: Color | undefined;

  cartas: Carta[] = [
    { img: 'assets/img/Infanteria.PNG', titulo: 'Infantería', tipo: 'Infanteria' },
    { img: 'assets/img/Caballeria.PNG', titulo: 'Caballería', tipo: 'Caballeria' },
    { img: 'assets/img/Artilleria.PNG', titulo: 'Artillería', tipo: 'Artilleria' }
  ];

  constructor(
    private route: ActivatedRoute,
    private partidaService: PartidaService,
    private juegoService: JuegoService,
    private router: Router,
    private seleccionPaisService: SeleccionPaisService,
    private botService: BotService,
    private paisService: PaisService,
    private juegoComponent: PantallaJuegoComponent
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.partidaId = +idParam!;
    this.cargarPartida();

    this.seleccionPaisService.paisSeleccionado$.subscribe(nombrePais => {
      if (!nombrePais) return;
      const paisEncontrado = this.paisesJugador.find(
        p => p.nombre.toLowerCase().trim() === nombrePais.toLowerCase().trim()
      );
      if (!paisEncontrado) {
        console.warn('No se encontró el país entre los del jugador');
        return;
      }

      this.paisSeleccionado = paisEncontrado;
      this.vecinoSeleccionado = null;

      this.paisService.obtenerPaisesVecinos(paisEncontrado.nombre).subscribe({
        next: (vecinos) => {
          const nombresPaisesJugador = this.paisesJugador.map(p => p.nombre);
          this.paisesVecinos = vecinos.filter(v => nombresPaisesJugador.includes(v.nombre));
        },
        error: (err) => {
          console.error('Error al obtener vecinos:', err);
          this.paisesVecinos = [];
        }
      });

      this.paisService.obtenerTropas(paisEncontrado.nombre).subscribe({
        next: (tropas) => {
          this.ejercitosDisponibles = tropas - 1;
          this.generarOpcionesEjercitos();
        },
        error: (err) => {
          console.error('Error al obtener tropas:', err);
          this.ejercitosDisponibles = 0;
        }
      });
    });
  }

  generarOpcionesEjercitos(): void {
    this.opcionesEjercitos = [];
    for (let i = 1; i <= this.ejercitosDisponibles; i++) {
      this.opcionesEjercitos.push(i);
    }
  }

  cargarPartida(): void {
    this.partidaService.cargarPartida(this.partidaId).subscribe(partida => {
      if (partida.estado === EstadoPartida.TERMINADA) {
        console.log('Partida terminada, llamando finalizarJuego()');
        this.juegoComponent.finalizarJuego();
        return;
      }
      console.log('Partida cargada:', partida);
      this.jugadores = partida.jugadores;
      this.juegoService.setJugadores(this.jugadores);

      const jugadorActualId = partida.jugadorActualId;
      const jugadorActual = this.jugadores.find(j => j.id === jugadorActualId);

      if (jugadorActual) {
        this.paisesJugador = jugadorActual.paises ?? [];
        this.objetivoActual = jugadorActual.objetivo;
        this.turnoJugador = jugadorActual.nombre;
        this.colorJugador = jugadorActual.color;
      } else {
        console.warn('Jugador actual no encontrado');
      }

      const paisesTemp: Pais[] = [];
      partida.jugadores.forEach(jugador => {
        jugador.paises.forEach((pais: any) => {
          paisesTemp.push({
            id: pais.nombre,
            nombre: pais.nombre,
            ejercitos: pais.ejercitos ?? 0,
            propietario: jugador.nombre,
            color: jugador.color
          });
        });
      });
      this.paises = paisesTemp;


      if (jugadorActual && jugadorActual.tipoJugador === 'BOT' && !this.isEjecutandoBot) {
        this.isEjecutandoBot = true;
        this.ejecutarTurnosBots();
      } else {
        this.isEjecutandoBot = false;
      }

      if (partida.fase === 'COLOCACION' && this.router.url !== `/juego/colocar/${this.partidaId}`) {
        this.router.navigate([`/juego/colocar/${this.partidaId}`]);
        return;
      }

      if (partida.fase === 'ATAQUE') {
        this.router.navigate([`/juego/atacar/${this.partidaId}`]);
        return;
      }

      if (partida.fase === 'REAGRUPACION') {
        this.router.navigate([`/juego/reagrupar/${this.partidaId}`]);
        return;
      }

    });

  }

  ejecutarTurnosBots() {
    this.isEjecutandoBot = true;
    this.botService.ejecutarTurnos(this.partidaId).subscribe({
      next: () => {
        console.log('Bots ejecutaron su turno (REAGRUPACION).');
        this.isEjecutandoBot = false;
        setTimeout(() => {this.cargarPartida();  this.isEjecutandoBot = false;}, 2500);
      },
      error: err => {
        console.error('Error al ejecutar turnos de bots:', err);
        alert('Error al ejecutar bots.');
        this.isEjecutandoBot = false;
      }
    });

  }

  alternarObjetivo() {
    this.mostrarObjetivo = !this.mostrarObjetivo;
  }

  abrirDialogoCartas() {
    this.dialogoCartas.nativeElement.showModal();
  }

  cerrarDialogo() {
    this.dialogoCartas.nativeElement.close();
  }

  pasarTurno() {
    this.partida = this.partidaService.getPartida(this.partidaId).subscribe(partida => {
      this.partidaService.pasarTurno(this.partidaId).subscribe({
        next: () => {
          if (partida.ronda < 4) {
            this.router.navigate([`juego/atacar/${this.partidaId}`]);
          }
          else {
            this.router.navigate([`juego/colocar/${this.partidaId}`]);
          }
          this.cargarPartida();
        },
        error: (err) => {
          console.error('Error al colocar tropas:', err);
          alert('Hubo un problema al pasar turno.');
        }
      });
    });
  }

  reagrupar() {
    if (!this.paisSeleccionado || !this.vecinoSeleccionado) {
      alert('Seleccioná un país origen y un país vecino destino.');
      return;
    }

    const origen = this.paisSeleccionado.nombre;
    const destino = this.vecinoSeleccionado.nombre;
    const tropas = this.cantidadEjercitos;

    this.partidaService.mover(this.partidaId, origen, destino, tropas).subscribe({
      next: () => {
        console.log(`Reagrupado con éxito.`);
        this.cargarPartida();
        this.pasarTurno();
      },
      error: (err) => {
        console.error('Error al realizar la reagrupación:', err);
        alert('Hubo un problema al realizar la reagrupación.');
      }
    });
  }
}
