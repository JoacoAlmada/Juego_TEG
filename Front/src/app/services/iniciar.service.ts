import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {agregarBotDTO, JugadorPartidaDTO} from '../interfaces/models';

@Injectable({
  providedIn: 'root'
})
export class IniciarService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  crearJugadorEnPartida(data: JugadorPartidaDTO) {
    return this.http.post<void>(this.baseUrl + '/Jugador/Partida', data);
  }

  iniciarPartida(id: number) {
    return this.http.post<void>(this.baseUrl + `/Partida/${id}/iniciar`, null);
  }

  agregarBots(id: number, data: agregarBotDTO) {
    return this.http.post(this.baseUrl + `/Bot/agregar/${id}`, data, { responseType: 'text' as 'json' });
  }

}
