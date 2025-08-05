import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class BotService {
  private baseUrl = 'http://localhost:8080/api/Bot'; // Ajustá si cambia

  constructor(private http: HttpClient) {}

  ejecutarTurnos(idPartida: number) {
    return this.http.post(`${this.baseUrl}/turno/${idPartida}`, {});
  }
}
