import {Component, EventEmitter, Output} from '@angular/core';
import {Router} from '@angular/router';


@Component({
  selector: 'app-reglas',
  standalone: true,
  imports: [],
  templateUrl: './reglas.component.html',
  styleUrls: ['./reglas.component.css'],
})
export class ReglasComponent {

  constructor(private router: Router,) {}
  @Output() cerrarReglas = new EventEmitter<void>();


  volver(): void {
    this.cerrarReglas.emit();
  }


}
