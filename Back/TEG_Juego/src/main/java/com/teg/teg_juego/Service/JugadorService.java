package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.jugadorRepository;
import com.teg.teg_juego.Repository.partidaRepository;
import com.teg.teg_juego.model.DTO.jugadorDTO;
import com.teg.teg_juego.model.DTO.jugadorPartidaDTO;
import com.teg.teg_juego.model.entities.Jugador;
import com.teg.teg_juego.model.entities.Partida;
import com.teg.teg_juego.model.enums.EstadoJugador;
import com.teg.teg_juego.model.enums.TipoJugador;
import com.teg.teg_juego.model.mappers.JugadorMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JugadorService {

    @Autowired
    private jugadorRepository JugadorRepository;
    @Autowired
    private partidaRepository PartidaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<jugadorDTO> getAllJugadores() {
        List<Jugador> jugadores = JugadorRepository.findAll();
        return jugadores.stream()
                .map(JugadorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public jugadorDTO getJugadorById(Integer id) {
        Jugador jugador = JugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        return JugadorMapper.toDTO(jugador);
    }

    public jugadorDTO createJugador(jugadorDTO jugadorDTO) {
        Jugador jugador = new Jugador();
        jugador.setNombreJ(jugadorDTO.getNombre());
        jugador.setColor(jugadorDTO.getColor());
        jugador.setFichasJ(jugadorDTO.getFichas() != null ? jugadorDTO.getFichas() : 0);
        jugador.setEstadoJ(EstadoJugador.ACTIVO);


        Jugador saved = JugadorRepository.save(jugador);


        saved.setTipoJ(TipoJugador.HUMANO);

        return modelMapper.map(saved, jugadorDTO.class);
    }

    public jugadorDTO updateJugador(Integer id, Jugador jugadorActualizado) {
        Jugador jugador = JugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        jugador.setNombreJ(jugadorActualizado.getNombreJ());
        jugador.setColor(jugadorActualizado.getColor());
        jugador.setFichasJ(jugadorActualizado.getFichasJ());
        jugador.setTipoJ(jugadorActualizado.getTipoJ());
        jugador.setEstadoJ(jugadorActualizado.getEstadoJ());
        Jugador actualizado = JugadorRepository.save(jugador);
        return modelMapper.map(actualizado, jugadorDTO.class);
    }

    public void deleteJugador(Integer id) {
        JugadorRepository.deleteById(id);
    }

    public List<jugadorDTO> getJugadoresDePartida(Integer idPartida) {
        Partida partida = PartidaRepository.findById(idPartida)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        return partida.getJugadores().stream()
                .map(j -> modelMapper.map(j, jugadorDTO.class))
                .toList();
    }

    public jugadorDTO agregarJugadorAPartida(Integer partidaId, Jugador jugador) {

        Partida partida = PartidaRepository.findById(partidaId)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        jugador.setPartida(partida);

        Jugador jugadorGuardado = JugadorRepository.save(jugador);

        partida.getJugadores().add(jugadorGuardado);

        return new jugadorDTO(jugadorGuardado);
    }

    public void crearJugadorEnPartida(jugadorPartidaDTO jugadorPartidaDTO) {
        List<jugadorDTO> jugadores = jugadorPartidaDTO.getJugadores();
        Partida partida = PartidaRepository.findById(jugadorPartidaDTO.getPartidaId()).orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        for (jugadorDTO jugadorDTO : jugadores) {
            Jugador jugador = new Jugador();
            jugador.setNombreJ(jugadorDTO.getNombre());
            jugador.setColor(jugadorDTO.getColor());
            jugador.setPartida(partida);

            JugadorRepository.save(jugador);
        }
    }
}
