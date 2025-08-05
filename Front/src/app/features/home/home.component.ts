import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {PartidaService} from '../../services/Partida.service';
import {SesionService} from '../../services/sesion.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  userName: string = '';
  userLevel: number = 1;
  userProgress: number = 0;

  constructor(
    private sesionService: SesionService,
    private partidaService: PartidaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.sesionService.estaLogueado()) {
      this.router.navigate(['/login']);
    }
    this.loadUserData();
    this.animateProgressBar();
  }


  startNewGame(): void {
    const nuevaPartida = {
      estadoPartida: 'EN_JUEGO',
      cantidadJugadores: 0,
      ronda: 1,
      fase: 'COLOCACION'
    };

    this.partidaService.crearPartida(nuevaPartida).subscribe({
      next: (partidaId) => {
        console.log('Partida creada:', partidaId);

        this.router.navigate(['/iniciar-partida', partidaId]);
      },
      error: (error) => {
        console.error('Error al crear partida:', error);
        alert('No se pudo crear la partida.');
      }
    });
  }

  resumeGame(): void {
    console.log('Reanudando partida...');
    this.partidaService.reanudarPartida().subscribe({
      next: (partida) => {
        console.log('Reanudando partida:', partida);
        this.router.navigate(['/juego/colocar/' + partida.id]);
      },
      error: (error) => {
        console.error('Error al reanudar partida: ', error);
      }
    })
  }

  closeSession(): void {
    const confirmClose = confirm('¿Estás seguro de que quieres cerrar sesión?');

    if (confirmClose) {
      console.log('Cerrando sesión...');

      this.sesionService.cerrarSesion();
      this.clearSessionData();

      this.router.navigate(['/login']);
      alert('Sesión cerrada exitosamente.');
    }
  }

  private loadUserData(): void {
    const sessionData = localStorage.getItem('userSession');

    if (sessionData) {
      const user = JSON.parse(sessionData);
      this.userName = user.nombre;
      this.userLevel = user.level || 1;
      this.userProgress = user.progress || 75;
    }
  }


  private hasSavedGames(): boolean {
    return false;
  }


  private clearSessionData(): void {
    localStorage.removeItem('userSession');
  }


  private animateProgressBar(): void {
    let currentProgress = 0;
    const targetProgress = this.userProgress;

    const interval = setInterval(() => {
      if (currentProgress >= targetProgress) {
        clearInterval(interval);
      } else {
        currentProgress++;
        this.userProgress = currentProgress;
      }
    }, 20);
  }
}
