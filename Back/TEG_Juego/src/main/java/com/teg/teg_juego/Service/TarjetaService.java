package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.jugadorRepository;
import com.teg.teg_juego.Repository.tarjetaPaisRepository;
import com.teg.teg_juego.model.DTO.tarjetaDTO;
import com.teg.teg_juego.model.entities.Jugador;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarjetaService {
    @Autowired
    private tarjetaPaisRepository TarjetaRepository;

    @Autowired
    private jugadorRepository JugadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<tarjetaDTO> getAll() {
        return TarjetaRepository.findAll().stream()
                .map(user -> modelMapper.map(user, tarjetaDTO.class))
                .collect(Collectors.toList());
    }

    public List<tarjetaDTO> getTarjetasJugador(Integer idJugador) {
        Jugador jugador = JugadorRepository.findById(idJugador).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        return jugador.getTarjetas().stream().map(t -> modelMapper.map(t, tarjetaDTO.class)).collect(Collectors.toList());
    }
}
