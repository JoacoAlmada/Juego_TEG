package com.teg.teg_juego.model.DTO;



import java.util.List;

import com.teg.teg_juego.model.entities.Jugador;
import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.EstadoJugador;
import com.teg.teg_juego.model.enums.TipoJugador;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class jugadorDTO {
    private Integer id;
    private String nombre;
    private Color color;
    private Integer fichas;
    private TipoJugador tipoJugador;
    private EstadoJugador estado;
    private List<paisDTO> paises;
    private objetivoDTO objetivo;
    private List<tarjetaDTO> tarjetas;

    public jugadorDTO(Jugador jugador) {
        this.id = jugador.getId();
        this.nombre = jugador.getNombreJ();
        this.color = jugador.getColor();
        this.fichas = jugador.getFichasJ();
        this.tipoJugador = jugador.getTipoJ();
        this.estado = jugador.getEstadoJ();
    }
}
