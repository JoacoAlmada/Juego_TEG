import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import { FormsModule } from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import {LoginService} from '../../../services/Login.service';
import { SesionService } from '../../../services/sesion.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData = { nombre: '', contrasenia: '' };

  constructor(
    private LoginService: LoginService,
    private router: Router,
    private sesionService: SesionService
  ) {}

  onSubmit() {
    if (!this.loginData.nombre || !this.loginData.contrasenia) {
      alert('Por favor completa todos los campos');
      return;
    }

    console.log('Payload que se va a enviar:', this.loginData);

    this.LoginService.login(this.loginData).subscribe({
      next: (res) => {
        this.sesionService.setNombreUsuario(this.loginData.nombre);
        console.log('Respuesta del login:', res);
        localStorage.setItem('userSession', JSON.stringify(res));

        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error('Error en login:', err);
        alert('Credenciales inválidas o error del servidor');
      }
    });
  }

}

