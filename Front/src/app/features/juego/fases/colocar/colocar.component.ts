import {Component, OnInit, ElementRef, ViewChild} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {
  Carta, Color,
  EstadoPartida,
  JugadorDTO,
  ObjetivoDTO,
  Pais,
  PaisDTO,
  PartidaDTO,
  TipoCarta
} from '../../../../interfaces/models';
import {PartidaService} from '../../../../services/Partida.service';
import {JuegoService} from '../../../../services/juego.service';
import {Subscription} from 'rxjs';
import {SeleccionPaisService} from '../../../../services/SeleccionarPaisService';
import {BotService} from '../../../../services/bot.service';
import {PantallaJuegoComponent} from '../../pantalla-juego/pantalla-juego.component';

@Component({
  selector: 'app-colocar',
  standalone: true,
  imports: [FormsModule, CommonModule, NgOptimizedImage],
  templateUrl: './colocar.component.html',
  styleUrl: './colocar.component.css'
})

export class ColocarComponent implements OnInit {
  jugadores: JugadorDTO[] = [];
  partidaId!: number;
  paises: Pais[] = [];
  partida: Subscription | undefined;
  opcionesEjercitos: number[] = [];
  paisesJugador: PaisDTO[] = [];
  objetivoActual: ObjetivoDTO | undefined;

  @ViewChild('dialogoCartas', { static: false })
  dialogoCartas!: ElementRef<HTMLDialogElement>;

  mostrarObjetivo = false;
  mostrarCartas = false;
  cantidadEjercitos = 1;
  ejercitosDisponibles = 1;
  paisSeleccionado: any = null;
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
    private juegoComponent: PantallaJuegoComponent
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.partidaId = +idParam!;
    this.cargarPartida();

    this.seleccionPaisService.paisSeleccionado$.subscribe(nombrePais => {
      if (nombrePais) {
        const paisEncontrado = this.paisesJugador.find(p => p.nombre === nombrePais);
        if (paisEncontrado) {
          this.paisSeleccionado = paisEncontrado;
        }
      }
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
        this.ejercitosDisponibles = jugadorActual.fichas ?? 0;
        this.generarOpcionesEjercitos();
        this.objetivoActual = jugadorActual.objetivo;
        this.paisesJugador = jugadorActual.paises ?? [];
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
      if (jugadorActual) {
        console.log('Jugador actual:', jugadorActual.nombre, jugadorActual.tipoJugador);
      }
      if (jugadorActual && jugadorActual.tipoJugador?.toUpperCase() === 'BOT') {
        if (!this.isEjecutandoBot) {
          console.log('Es turno de bot, ejecutando...');
          this.isEjecutandoBot = true;
          this.ejecutarTurnosBots();
        } else {
          console.log('Bot ya ejecutando...');
        }
      } else {
        this.isEjecutandoBot = false;
      }
    });
  }

  ejecutarTurnosBots() {
    this.isEjecutandoBot = true;
    this.botService.ejecutarTurnos(this.partidaId).subscribe({
      next: () => {
        console.log('Bots ejecutaron su turno. Verificando siguiente jugador...');
        setTimeout(() => {this.cargarPartida();    this.isEjecutandoBot = false;
        }, 2500);
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

  canjear() {
    const conteos: Record<TipoCarta, number> = { Infanteria: 0, Caballeria: 0, Artilleria: 0 };
    this.cartas.forEach(c => conteos[c.tipo]++);
    const combinacionValida =
      (conteos.Infanteria >= 1 && conteos.Caballeria >= 1 && conteos.Artilleria >= 1) ||
      conteos.Infanteria >= 3 || conteos.Caballeria >= 3 || conteos.Artilleria >= 3;

    if (combinacionValida) {
      this.ejercitosDisponibles += 5;
      this.eliminarCartasCanjeadas(conteos);
      alert('¡Canje exitoso! Ganaste 5 ejércitos.');
    } else {
      alert('No tienes una combinación válida para canjear.');
    }

    this.cerrarDialogo();
  }

  eliminarCartasCanjeadas(conteos: Record<TipoCarta, number>) {
    if (conteos.Infanteria >= 1 && conteos.Caballeria >= 1 && conteos.Artilleria >= 1) {
      this.cartas = this.eliminarUnaDeCadaTipo();
    } else if (conteos.Infanteria >= 3) {
      this.cartas = this.eliminarTres('Infanteria');
    } else if (conteos.Caballeria >= 3) {
      this.cartas = this.eliminarTres('Caballeria');
    } else if (conteos.Artilleria >= 3) {
      this.cartas = this.eliminarTres('Artilleria');
    }
  }

  eliminarUnaDeCadaTipo(): Carta[] {
    const usados = new Set<TipoCarta>();
    return this.cartas.filter(carta => {
      if (!usados.has(carta.tipo) && usados.size < 3) {
        usados.add(carta.tipo);
        return false;
      }
      return true;
    });
  }

  eliminarTres(tipo: TipoCarta): Carta[] {
    let eliminados = 0;
    return this.cartas.filter(carta => {
      if (carta.tipo === tipo && eliminados < 3) {
        eliminados++;
        return false;
      }
      return true;
    });
  }

  colocarEjercitos() {
    if (!this.paisSeleccionado) {
      alert('Debes seleccionar un país para colocar tropas.');
      return;
    }
    const pais = this.paisSeleccionado.nombre;
    const tropas = this.cantidadEjercitos;

    this.partidaService.colocar(this.partidaId, pais, tropas).subscribe({
      next: () => {
        console.log(`Colocadas ${tropas} tropas en el país ${pais}`);
        this.cargarPartida();
      },
      error: (err) => {
        console.error('Error al colocar tropas:', err);
        alert('Hubo un problema al colocar tropas.');
      }
    });
    this.cantidadEjercitos = 1;
  }

  avanzarFase() {
    this.partida = this.partidaService.getPartida(this.partidaId).subscribe(partida => {
      if (partida.ronda < 3) {
        this.partidaService.pasarTurno(this.partidaId).subscribe({
          next: () => {
            if (partida.ronda == 2 && partida.turno == partida.jugadores.length - 1)
              this.router.navigate([`juego/atacar/${this.partidaId}`]);
            this.cargarPartida();

          },
          error: (err) => {
            console.error('Error al pasar turno:', err);
            alert('Error al pasar turno');
          }
        });
      } else {
        this.partidaService.avanzarFase(this.partidaId).subscribe({
          next: () => {
            this.cargarPartida();
          },
          error: (err) => {
            console.error('Error al avanzar fase:', err);
            alert('Error al avanzar de fase');
          }
        });
      }
    });
  }
}
