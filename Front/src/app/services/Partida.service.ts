import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {JugadorDTO, PartidaDTO, ResultadoAtaque} from '../interfaces/models';

@Injectable({
  providedIn: 'root'
})
export class PartidaService {

  private apiUrl = 'http://localhost:8080/api/Partida';

  constructor(private http: HttpClient) {}

    crearPartida(partida: {
        estadoPartida: string;
        cantidadJugadores: number;
        ronda: number;
        fase: string
    }): Observable<PartidaDTO> {
    return this.http.post<PartidaDTO>(this.apiUrl, partida);
  }

  cargarPartida(id: number): Observable<PartidaDTO> {
    return this.http.get<PartidaDTO>(`${this.apiUrl}/${id}/cargar`);
  }

  colocar(partidaId: number, pais: string, tropas: number) {
    return this.http.post<void>(`${this.apiUrl}/${partidaId}/colocar`, {
      pais,
      tropas
    });
  }

  atacar(id: number, origen: string, destino: string): Observable<ResultadoAtaque> {
    return this.http.post<ResultadoAtaque>(`${this.apiUrl}/${id}/atacar`, {
      origen,
      destino
    });
  }

  mover(id: number, origen: string, destino: string, tropas: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${id}/mover`, {
      origen,
      destino,
      tropas
    });
  }

  avanzarFase(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/fase`, {});
  }

  pasarTurno(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/turno`, {});
  }

  getPartida(id: number): Observable<PartidaDTO> {
    return this.http.get<PartidaDTO>(`${this.apiUrl}/${id}/cargar`);
  }

  getGanador(partidaId: number): Observable<JugadorDTO> {
    return this.http.get<JugadorDTO>(`/partidas/${partidaId}/ganador`);
  }

  reanudarPartida(): Observable<PartidaDTO> {
    return this.http.get<PartidaDTO>(`${this.apiUrl}/reanudar`);
  }
}
