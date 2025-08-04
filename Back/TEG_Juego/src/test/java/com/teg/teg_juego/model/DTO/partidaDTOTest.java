package com.teg.teg_juego.model.DTO;


import com.teg.teg_juego.model.enums.EstadoPartida;
import com.teg.teg_juego.model.enums.Fase;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class partidaDTOTest {

    private jugadorDTO jugadorConId(int id) {
        jugadorDTO jugador = new jugadorDTO();
        jugador.setId(id);
        return jugador;
    }

    @Test
    void testConstructorVacioYSetters() {
        partidaDTO dto = new partidaDTO();
        dto.setId(1);
        dto.setEstado(EstadoPartida.EN_JUEGO);
        dto.setCantidadJugadores(3);
        dto.setJugadorActualNombre("Jugador 1");
        dto.setRonda(2);
        dto.setTurno(1);
        dto.setFase(Fase.ATAQUE);

        jugadorDTO jugador1 = jugadorConId(10);
        jugadorDTO jugador2 = jugadorConId(20);

        dto.setJugadores(Arrays.asList(jugador1, jugador2));

        assertEquals(Integer.valueOf(1), dto.getId());
        assertEquals(EstadoPartida.EN_JUEGO, dto.getEstado());
        assertEquals(Integer.valueOf(3), dto.getCantidadJugadores());
        assertEquals("Jugador 1", dto.getJugadorActualNombre());
        assertEquals(Integer.valueOf(2), dto.getRonda());
        assertEquals(Integer.valueOf(1), dto.getTurno());
        assertEquals(Fase.ATAQUE, dto.getFase());
        assertEquals(2, dto.getJugadores().size());
    }

    @Test
    void testConstructorConArgumentos() {
        jugadorDTO j1 = jugadorConId(1);
        jugadorDTO j2 = jugadorConId(2);
        List<jugadorDTO> jugadores = Arrays.asList(j1, j2);

        partidaDTO dto = new partidaDTO(5, EstadoPartida.TERMINADA, 2, null, "Jugador B", 3, 0, Fase.REAGRUPACION, jugadores);

        assertEquals(Integer.valueOf(5), dto.getId());
        assertEquals(EstadoPartida.TERMINADA, dto.getEstado());
        assertEquals(Integer.valueOf(2), dto.getCantidadJugadores());
        assertEquals("Jugador B", dto.getJugadorActualNombre());
        assertEquals(Integer.valueOf(3), dto.getRonda());
        assertEquals(Integer.valueOf(0), dto.getTurno());
        assertEquals(Fase.REAGRUPACION, dto.getFase());
        assertEquals(2, dto.getJugadores().size());
    }

    @Test
    void testGetJugadorActualIdNormal() {
        jugadorDTO j1 = jugadorConId(100);
        jugadorDTO j2 = jugadorConId(200);

        partidaDTO dto = new partidaDTO();
        dto.setJugadores(Arrays.asList(j1, j2));
        dto.setTurno(1);

        assertEquals(Integer.valueOf(200), dto.getJugadorActualId());
    }

    @Test
    void testGetJugadorActualIdConTurnoFueraDeRango() {
        jugadorDTO j1 = jugadorConId(100);
        partidaDTO dto = new partidaDTO();
        dto.setJugadores(Collections.singletonList(j1));
        dto.setTurno(5); // fuera de rango

        assertNull(dto.getJugadorActualId());
    }

    @Test
    void testGetJugadorActualIdConListaVacia() {
        partidaDTO dto = new partidaDTO();
        dto.setJugadores(Collections.emptyList());
        dto.setTurno(0);

        assertNull(dto.getJugadorActualId());
    }

    @Test
    void testGetJugadorActualIdConListaNull() {
        partidaDTO dto = new partidaDTO();
        dto.setJugadores(null);
        dto.setTurno(0);

        assertNull(dto.getJugadorActualId());
    }

    @Test
    void testEqualsHashCodeAndToString() {
        jugadorDTO j1 = jugadorConId(1);
        partidaDTO dto1 = new partidaDTO(1, EstadoPartida.EN_JUEGO, 1, null, "Jugador A", 1, 0, Fase.COLOCACION, List.of(j1));
        partidaDTO dto2 = new partidaDTO(1, EstadoPartida.EN_JUEGO, 1, null, "Jugador A", 1, 0, Fase.COLOCACION, List.of(j1));

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertTrue(dto1.toString().contains("Jugador A"));
    }
}