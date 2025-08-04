package com.teg.teg_juego.model.DTO;


import com.teg.teg_juego.model.enums.EstadoPartida;
import com.teg.teg_juego.model.enums.Fase;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class partidaDTO {
    private Integer id;
    private EstadoPartida estado;
    private Integer cantidadJugadores;
    private Integer jugadorActualId;
    private String jugadorActualNombre;
    private Integer ronda;
    private Integer turno;
    private Fase fase;
    private List<jugadorDTO> jugadores;

    public Integer getJugadorActualId() {
        if (jugadores == null || jugadores.isEmpty() || turno == null || turno >= jugadores.size()) {
            return null;
        }
        return jugadores.get(turno).getId();
    }
}
