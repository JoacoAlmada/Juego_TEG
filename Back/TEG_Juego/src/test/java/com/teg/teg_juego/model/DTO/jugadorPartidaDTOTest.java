package com.teg.teg_juego.model.DTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class jugadorPartidaDTOTest {

    @Test
    public void testConstructorSinParametrosYSetters() {
        // Arrange & Act
        jugadorPartidaDTO dto = new jugadorPartidaDTO();

        // Datos de prueba
        List<jugadorDTO> jugadores = new ArrayList<>();
        jugadores.add(new jugadorDTO());
        jugadores.add(new jugadorDTO());

        Integer partidaId = 123;

        // Setear valores
        dto.setJugadores(jugadores);
        dto.setPartidaId(partidaId);

        // Assert
        assertEquals(jugadores, dto.getJugadores());
        assertEquals(partidaId, dto.getPartidaId());
        assertEquals(2, dto.getJugadores().size());
    }

    @Test
    public void testConstructorConParametros() {
        // Arrange
        List<jugadorDTO> jugadores = new ArrayList<>();
        jugadores.add(new jugadorDTO());
        jugadores.add(new jugadorDTO());
        jugadores.add(new jugadorDTO());

        Integer partidaId = 456;

        // Act
        jugadorPartidaDTO dto = new jugadorPartidaDTO(jugadores, partidaId);

        // Assert
        assertEquals(jugadores, dto.getJugadores());
        assertEquals(partidaId, dto.getPartidaId());
        assertEquals(3, dto.getJugadores().size());
    }

    @Test
    public void testSettersConValoresNull() {
        // Arrange
        jugadorPartidaDTO dto = new jugadorPartidaDTO();

        // Primero setear valores no null
        List<jugadorDTO> jugadores = new ArrayList<>();
        jugadores.add(new jugadorDTO());
        dto.setJugadores(jugadores);
        dto.setPartidaId(100);

        // Act - Setear valores null
        dto.setJugadores(null);
        dto.setPartidaId(null);

        // Assert
        assertNull(dto.getJugadores());
        assertNull(dto.getPartidaId());
    }

    @Test
    public void testListaVaciaDeJugadores() {
        // Arrange
        jugadorPartidaDTO dto = new jugadorPartidaDTO();
        List<jugadorDTO> jugadoresVacios = new ArrayList<>();

        // Act
        dto.setJugadores(jugadoresVacios);
        dto.setPartidaId(555);

        // Assert
        assertNotNull(dto.getJugadores());
        assertTrue(dto.getJugadores().isEmpty());
        assertEquals(0, dto.getJugadores().size());
        assertEquals(555, dto.getPartidaId());
    }

    @Test
    public void testConstructorConParametrosNull() {
        // Act
        jugadorPartidaDTO dto = new jugadorPartidaDTO(null, null);

        // Assert
        assertNull(dto.getJugadores());
        assertNull(dto.getPartidaId());
    }

    @Test
    public void testModificacionDeLista() {
        // Arrange
        jugadorPartidaDTO dto = new jugadorPartidaDTO();
        List<jugadorDTO> jugadores = new ArrayList<>();
        jugadores.add(new jugadorDTO());

        // Act
        dto.setJugadores(jugadores);

        // Modificar la lista original
        jugadores.add(new jugadorDTO());

        // Assert - La modificación se refleja porque es la misma referencia
        assertEquals(2, dto.getJugadores().size());

        // Modificar la lista obtenida del getter
        List<jugadorDTO> jugadoresObtenidos = dto.getJugadores();
        jugadoresObtenidos.add(new jugadorDTO());

        assertEquals(3, dto.getJugadores().size());
    }
}