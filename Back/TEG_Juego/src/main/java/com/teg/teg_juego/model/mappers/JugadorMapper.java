package com.teg.teg_juego.model.mappers;



import com.teg.teg_juego.model.DTO.jugadorDTO;
import com.teg.teg_juego.model.entities.Jugador;

import java.util.stream.Collectors;

public class JugadorMapper {

    public static jugadorDTO toDTO(Jugador j) {
        jugadorDTO jDTO = new jugadorDTO();
        jDTO.setId(j.getId());
        jDTO.setNombre(j.getNombreJ());
        jDTO.setColor(j.getColor());
        jDTO.setFichas(j.getFichasJ());
        jDTO.setTipoJugador(j.getTipoJ());
        jDTO.setEstado(j.getEstadoJ());
        jDTO.setObjetivo(ObjetivoMapper.toDTO(j.getObjetivo()));
        if (j.getTarjetas() != null) {
            jDTO.setTarjetas(j.getTarjetas().stream()
                    .map(TarjetaMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        if (j.getPaises() != null) {
            jDTO.setPaises(j.getPaises().stream()
                    .map(PaisMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return jDTO;
    }
}
