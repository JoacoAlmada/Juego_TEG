package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.JugadorService;
import com.teg.teg_juego.model.DTO.jugadorDTO;
import com.teg.teg_juego.model.DTO.jugadorPartidaDTO;
import com.teg.teg_juego.model.entities.Jugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JugadorControllerTest {

    @InjectMocks
    private JugadorController jugadorController;

    @Mock
    private JugadorService jugadorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        jugadorDTO jugador1 = new jugadorDTO();
        jugador1.setNombre("Carlitos");

        jugadorDTO jugador2 = new jugadorDTO();
        jugador2.setNombre("Tercero");

        when(jugadorService.getAllJugadores()).thenReturn(List.of(jugador1, jugador2));

        List<jugadorDTO> jugadores = jugadorController.getAll();

        assertEquals(2, jugadores.size());
        assertEquals("Carlitos", jugadores.get(0).getNombre());
    }

    @Test
    void getById() {
        jugadorDTO jugador = new jugadorDTO();
        jugador.setNombre("Carlitos");

        when(jugadorService.getJugadorById(1)).thenReturn(jugador);

        jugadorDTO result = jugadorController.getById(1);

        assertEquals("Carlitos", result.getNombre());
    }

    @Test
    void createJugador() {
        jugadorDTO jugador = new jugadorDTO();
        jugador.setNombre("NuevoJugador");

        when(jugadorService.createJugador(jugador)).thenReturn(jugador);

        ResponseEntity<jugadorDTO> response = jugadorController.createJugador(jugador);

        assertEquals("NuevoJugador", response.getBody().getNombre());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void update() {
        Jugador jugadorEntity = new Jugador();
        jugadorEntity.setNombreJ("Actualizado");

        jugadorDTO jugadorDTO = new jugadorDTO();
        jugadorDTO.setNombre("Actualizado");

        when(jugadorService.updateJugador(1, jugadorEntity)).thenReturn(jugadorDTO);

        jugadorDTO result = jugadorController.update(1, jugadorEntity);

        assertEquals("Actualizado", result.getNombre());
    }

    @Test
    void delete() {
        jugadorController.delete(1);
        verify(jugadorService, times(1)).deleteJugador(1);
    }

    @Test
    void getByPartida() {
        jugadorDTO jugador = new jugadorDTO();
        jugador.setNombre("Carlitos");

        when(jugadorService.getJugadoresDePartida(5)).thenReturn(List.of(jugador));

        List<jugadorDTO> jugadores = jugadorController.getByPartida(5);

        assertEquals(1, jugadores.size());
        assertEquals("Carlitos", jugadores.get(0).getNombre());
    }

    @Test
    void addJugadorAPartida() {
        Jugador jugadorEntity = new Jugador();
        jugadorEntity.setNombreJ("Nuevo");

        jugadorDTO jugadorDTO = new jugadorDTO();
        jugadorDTO.setNombre("Nuevo");

        when(jugadorService.agregarJugadorAPartida(2, jugadorEntity)).thenReturn(jugadorDTO);

        jugadorDTO result = jugadorController.addJugadorAPartida(2, jugadorEntity);

        assertEquals("Nuevo", result.getNombre());
    }

    @Test
    void crearJugadorEnPartida() {
        jugadorPartidaDTO dto = new jugadorPartidaDTO();

        jugadorController.crearJugadorEnPartida(dto);

        verify(jugadorService, times(1)).crearJugadorEnPartida(dto);
    }
}
