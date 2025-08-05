import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface ILogin {
  nombre: string;
  contrasenia: string;
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private baseUrl = 'http://localhost:8080/api/Usuario';

  constructor(private http: HttpClient) {}

  login(data: ILogin): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, data);
  }
}
