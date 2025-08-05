import {Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {JugadorDTO} from '../../../interfaces/models';

@Component({
  selector: 'app-mapa',
  imports: [],
  templateUrl: './mapa.component.html',
  styleUrl: './mapa.component.css'
})
export class MapaComponent implements OnChanges {
  @ViewChild('mapContainer', { static: true }) mapContainer!: ElementRef;

  @Input() jugadores: JugadorDTO[] = [];
  @Output() paisSeleccionado = new EventEmitter<string>();

  coloresCSS: { [key: string]: string } = {
    ROJO: 'red',
    AZUL: 'blue',
    VERDE: 'green',
    AMARILLO: 'yellow',
    NEGRO: 'black',
    MAGENTA: 'magenta'
  };

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['jugadores'] && this.jugadores?.length > 0) {
      console.log('Jugadores recibidos en MapaComponent:', this.jugadores);
      this.pintarMapa();
    }
  }

  pintarMapa(): void {
    setTimeout(() => {
      const svg = this.mapContainer?.nativeElement.querySelector('svg');
      if (!svg) {
        console.warn('SVG no encontrado dentro de mapContainer');
        return;
      }


      svg.querySelectorAll('.army-count').forEach((el: Element) => el.remove());

      this.jugadores.forEach(jugador => {
        const colorCSS = this.coloresCSS[jugador.color.toUpperCase()] || '#ccc';

        jugador.paises.forEach((pais: any) => {
          const path = svg.querySelector(`[data-country="${pais.nombre}"]`);
          if (path) {
            path.setAttribute('fill', colorCSS);
            path.style.cursor = 'pointer';
            path.onclick = () => this.paisSeleccionado.emit(pais.nombre);
            const text = document.createElementNS('http://www.w3.org/2000/svg', 'text');
            text.textContent = pais.ejercito?.toString() ?? '0';
            text.classList.add('army-count');

            const bbox = path.getBBox();
            const x = bbox.x + bbox.width / 2;
            const y = bbox.y + bbox.height / 2 + 4;

            text.setAttribute('x', x.toString());
            text.setAttribute('y', y.toString());
            text.setAttribute('text-anchor', 'middle');
            text.setAttribute('dominant-baseline', 'middle');
            text.setAttribute('fill', 'white');
            text.setAttribute('font-size', '12');
            text.setAttribute('font-weight', 'bold');
            text.setAttribute('pointer-events', 'none');

            path.parentElement?.appendChild(text);

            path.style.cursor = 'pointer';
            path.onclick = () => {
              this.paisSeleccionado.emit(pais.nombre);
            };
          } else {
            console.warn(`No se encontró el país en el SVG: ${pais.nombre}`);
          }
        });
      });
    }, 0);
  }
}

