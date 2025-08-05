import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SesionService {
  private nombreUsuario: string = '';

  constructor() {
    const usuarioGuardado = localStorage.getItem('userSession');
    if (usuarioGuardado) {
      const parsed = JSON.parse(usuarioGuardado);
      this.nombreUsuario = parsed?.nombre ?? '';
    }
  }

  setNombreUsuario(nombre: string): void {
    this.nombreUsuario = nombre;
    const data = JSON.parse(localStorage.getItem('userSession') || '{}');
    localStorage.setItem('userSession', JSON.stringify({ ...data, nombre }));
  }

  getNombreUsuario(): string {
    return this.nombreUsuario;
  }

  cerrarSesion(): void {
    this.nombreUsuario = '';
    localStorage.removeItem('userSession');
  }

  estaLogueado(): boolean {
    return this.nombreUsuario !== '';
  }
}
