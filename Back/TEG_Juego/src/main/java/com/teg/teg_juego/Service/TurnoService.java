package com.teg.teg_juego.Service;

import com.teg.teg_juego.Repository.partidaRepository;
import com.teg.teg_juego.model.DTO.turnoDTO;
import com.teg.teg_juego.model.entities.Partida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoService {
    @Autowired
    private partidaRepository PartidaRepository;

    public Integer getTurnoActual(Integer partidaId) {
        Partida partida = PartidaRepository.findById(partidaId)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        return partida.getTurno();
    }

    public turnoDTO getHistorialTurnos(Integer partidaId) {
        Partida partida = PartidaRepository.findById(partidaId)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        return new turnoDTO(
                partida.getRonda(),
                partida.getTurno(),
                partida.getFase()
        );
    }
}
