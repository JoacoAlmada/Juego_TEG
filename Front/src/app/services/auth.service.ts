import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface IRegister {
  nombre: string;
  contrasenia: string;
}
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/Usuario';

  constructor(private http: HttpClient) {}

  register(data: IRegister): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, data);
  }

  login(data: IRegister): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, data);
  }
}
