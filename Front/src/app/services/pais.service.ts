import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {PaisDTO} from '../interfaces/models';
import {Observable} from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PaisService {
  private baseUrl = 'http://localhost:8080/api/Pais';

  constructor(private http: HttpClient) {}

  obtenerPaisesVecinos(nombre: string): Observable<PaisDTO[]> {
    return this.http.get<PaisDTO[]>(`${this.baseUrl}/${nombre}/vecinos`);
  }

  obtenerTropas(nombre: string): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/${nombre}/tropas`);
  }
}
