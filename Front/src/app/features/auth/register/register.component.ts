import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {AuthService, IRegister} from '../../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'] //
})
export class RegisterComponent {
  registerData = {
    usuario: '',
    password: '',
    confirmPassword: ''
  };
  constructor(private authService: AuthService, private router: Router) {}

  volverAtras() {
    this.router.navigate(['/login']);
  }

  onSubmit() {
    if (!this.registerData.usuario || !this.registerData.password) {
      alert('Por favor completa todos los campos');
      return;
    }

    if (this.registerData.password !== this.registerData.confirmPassword) {
      alert('Las contraseñas no coinciden');
      return;
    }

    const payload: IRegister = {
      nombre: this.registerData.usuario,
      contrasenia: this.registerData.password
    };

    this.authService.register(payload).subscribe({
      next: (res) => {
        console.log('Respuesta registro:', res);
        alert('Usuario registrado exitosamente');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Error en registro:', err);
        alert('Error al registrar usuario. Intenta nuevamente.');
      }
    });
  }
}
