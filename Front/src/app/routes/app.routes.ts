import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import { LoginComponent } from '../features/auth/login/login.component';
import {RegisterComponent} from '../features/auth/register/register.component';
import { MapaComponent } from '../features/juego/mapa/mapa.component';
import {HomeComponent} from '../features/home/home.component';
import {IniciarPartidaComponent} from '../features/juego/iniciar-partida/iniciar-partida.component';
import {ColocarComponent} from '../features/juego/fases/colocar/colocar.component';
import {PantallaJuegoComponent} from '../features/juego/pantalla-juego/pantalla-juego.component';
import {ReglasComponent} from '../features/juego/reglas/reglas.component';
import {PausaComponent} from '../features/juego/pausa/pausa.component';
import {AtacarComponent} from '../features/juego/fases/atacar/atacar.component';
import {ReagruparComponent} from '../features/juego/fases/reagrupar/reagrupar.component';
import {AuthGuard} from '../features/auth/auth.guard';
import {FinalPartidaComponent} from '../features/juego/final-partida/final-partida.component';

export { routes };


const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'mapa', component: MapaComponent, canActivate: [AuthGuard] },
  { path: 'iniciar-partida/:id', component: IniciarPartidaComponent, canActivate: [AuthGuard] },
  {
    path: 'juego', component: PantallaJuegoComponent, canActivate: [AuthGuard], children: [
      { path: 'colocar/:id', component: ColocarComponent, canActivate: [AuthGuard] },
      { path: 'atacar/:id', component: AtacarComponent, canActivate: [AuthGuard] },
      { path: 'reagrupar/:id', component: ReagruparComponent, canActivate: [AuthGuard] }
    ]
  },
  { path: 'reglas', component: ReglasComponent, canActivate: [AuthGuard] },
  { path: 'pausa', component: PausaComponent, canActivate: [AuthGuard] },
  { path: 'final/:id', component: FinalPartidaComponent, canActivate: [AuthGuard] }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
