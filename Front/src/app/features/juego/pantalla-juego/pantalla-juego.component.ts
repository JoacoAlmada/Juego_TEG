import {AfterViewInit, Component, ElementRef, inject, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router, RouterOutlet} from '@angular/router';
import {MapaComponent} from '../mapa/mapa.component';
import {PausaComponent} from '../pausa/pausa.component';
import {CommonModule} from '@angular/common';
import {ReglasComponent} from '../reglas/reglas.component';
import {FinalPartidaComponent} from '../final-partida/final-partida.component';
import {JuegoService} from '../../../services/juego.service';
import {JugadorDTO, Pais} from '../../../interfaces/models';
import {SeleccionPaisService} from '../../../services/SeleccionarPaisService';
import { ChangeDetectorRef } from '@angular/core';
import {SesionService} from '../../../services/sesion.service';

@Component({
  selector: 'app-pantalla-juego',
  imports: [
    CommonModule,
    RouterOutlet,
    MapaComponent,
    PausaComponent,
    ReglasComponent,
    FinalPartidaComponent,

  ],
  templateUrl: './pantalla-juego.component.html',
  styleUrl: './pantalla-juego.component.css'
})
export class PantallaJuegoComponent implements AfterViewInit , OnInit{
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  jugadores: JugadorDTO[] = [];
  pausado: boolean = false;
  reglas: boolean = false;
  finalizado: boolean = false;
  paises: Pais[] = [];
  paisMapaSeleccionado: string = '';

  estaMuteado: boolean = false;

  @ViewChild('audioFondo') audioRef!: ElementRef<HTMLAudioElement>;

  constructor(
    private sesionService: SesionService,
    private juegoService: JuegoService,
    private seleccionPaisService: SeleccionPaisService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    if (!this.sesionService.estaLogueado()) {
      this.router.navigate(['/login']);
    }
    this.juegoService.jugadores$.subscribe(jugadores => {
      this.jugadores = jugadores;
      console.log('Jugadores recibidos en pantalla juego:', jugadores);
    });
  }
  onPaisMapaSeleccionado(nombrePais: string) {
    this.paisMapaSeleccionado = nombrePais;
    this.seleccionPaisService.seleccionarPais(nombrePais);
  }
  pausarMusica(): void {
    this.estaMuteado = !this.estaMuteado;
    const audio = this.audioRef.nativeElement;
    this.estaMuteado ? audio.pause() : audio.play();
  }

  ngAfterViewInit() {
    document.addEventListener('click', () => {
      this.audioRef.nativeElement.play();
    }, { once: true });
  }

  //menus

  pausarJuego(): void {
    this.pausado = true;
  }

  reanudarJuego(): void {
    this.pausado = false;
  }

  verReglas(): void {
    this.reglas = true;
  }
  volverReglas(): void {
    this.reglas = false;
  }

  abrirReglasDesdePausa(): void {
    this.pausado = false;
    this.verReglas();
  }

  finalizarJuego(): void {
    this.finalizado = true;
    this.cdr.detectChanges();
  }
  volverMenu(): void {
    this.reglas = false;
  }

  obtenerPaisPorId(id: string): Pais | null {
    return this.paises.find(c => c.id === id) || null;
  }
}
