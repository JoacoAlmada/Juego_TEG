package com.teg.teg_juego.model.mappers;


import com.teg.teg_juego.model.DTO.partidaDTO;
import com.teg.teg_juego.model.entities.Jugador;
import com.teg.teg_juego.model.entities.Partida;
import com.teg.teg_juego.model.enums.EstadoPartida;
import com.teg.teg_juego.model.enums.Fase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartidaMapperTest {

    private Partida partida;
    private List<Jugador> jugadores;

    @BeforeEach
    void setUp() {
        // Setup jugadores
        Jugador jugador1 = new Jugador();
        jugador1.setId(1);
        jugador1.setNombreJ("Juan");

        Jugador jugador2 = new Jugador();
        jugador2.setId(2);
        jugador2.setNombreJ("María");

        jugadores = Arrays.asList(jugador1, jugador2);

        // Setup partida
        partida = new Partida();
        partida.setId(1);
        partida.setEstadoPartida(EstadoPartida.EN_JUEGO);
        partida.setCantidadJugadores(2);
        partida.setId(1);
        partida.setRonda(3);
        partida.setTurno(1);
        partida.setFase(Fase.ATAQUE);
        partida.setJugadores(jugadores);
    }

    @Test
    void testToDTOCompleto() {
        partidaDTO resultado = PartidaMapper.toDTO(partida);

        assertNotNull(resultado);
        assertEquals(partida.getId(), resultado.getId());
        assertEquals(partida.getEstadoPartida(), resultado.getEstado());
        assertEquals(partida.getCantidadJugadores(), resultado.getCantidadJugadores());
        assertEquals(partida.getJugadorActualId(), resultado.getJugadorActualId());
        assertEquals(partida.getJugadorActualNombre(), resultado.getJugadorActualNombre());
        assertEquals(partida.getRonda(), resultado.getRonda());
        assertEquals(partida.getTurno(), resultado.getTurno());
        assertEquals(partida.getFase(), resultado.getFase());

        // Verificar jugadores mapeados
        assertNotNull(resultado.getJugadores());
        assertEquals(2, resultado.getJugadores().size());
    }


}