import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SeleccionPaisService {
  private paisSeleccionadoSubject = new BehaviorSubject<string | null>(null);
  paisSeleccionado$ = this.paisSeleccionadoSubject.asObservable();

  seleccionarPais(pais: string) {
    this.paisSeleccionadoSubject.next(pais);
  }
}
