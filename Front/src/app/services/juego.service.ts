import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {JugadorDTO} from '../interfaces/models';

@Injectable({
  providedIn: 'root'
})
export class JuegoService {
  private jugadoresSubject = new BehaviorSubject<JugadorDTO[]>([]);
  jugadores$ = this.jugadoresSubject.asObservable();

  setJugadores(jugadores: JugadorDTO[]) {
    this.jugadoresSubject.next(jugadores);
  }
}
