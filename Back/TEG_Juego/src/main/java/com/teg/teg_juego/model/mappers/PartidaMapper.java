package com.teg.teg_juego.model.mappers;



import com.teg.teg_juego.model.DTO.jugadorDTO;
import com.teg.teg_juego.model.DTO.partidaDTO;
import com.teg.teg_juego.model.entities.Partida;

import java.util.List;
import java.util.stream.Collectors;

public class PartidaMapper {

    public static partidaDTO toDTO(Partida partida) {
        partidaDTO dto = new partidaDTO();
        dto.setId(partida.getId());
        dto.setEstado(partida.getEstadoPartida());
        dto.setCantidadJugadores(partida.getCantidadJugadores());
        dto.setJugadorActualId(partida.getJugadorActualId());
        dto.setJugadorActualNombre(partida.getJugadorActualNombre());
        dto.setRonda(partida.getRonda());
        dto.setTurno(partida.getTurno());
        dto.setFase(partida.getFase());

        List<jugadorDTO> jugadoresDTO = partida.getJugadores().stream()
                .map(JugadorMapper::toDTO)
                .collect(Collectors.toList());

        dto.setJugadores(jugadoresDTO);

        return dto;
    }
}
