package com.teg.teg_juego.model.mappers;


import com.teg.teg_juego.model.DTO.paisDTO;
import com.teg.teg_juego.model.entities.Continente;
import com.teg.teg_juego.model.entities.Jugador;
import com.teg.teg_juego.model.entities.Pais;
import com.teg.teg_juego.model.enums.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaisMapperTest {

    private Pais pais;
    private Jugador jugador;
    private Continente continente;

    @BeforeEach
    void setUp() {
        jugador = new Jugador();
        jugador.setId(1);
        jugador.setNombreJ("Juan");
        jugador.setColor(Color.ROJO);

        continente = new Continente();
        continente.setId(1);
        continente.setNombre("América del Sur");

        pais = new Pais();
        pais.setId(1);
        pais.setNombre("Argentina");
        pais.setEjercito(5);
        pais.setJugador(jugador);
        pais.setContinente(continente);
    }

    @Test
    void testToDTOCompleto() {
        paisDTO resultado = PaisMapper.toDTO(pais);

        assertNotNull(resultado);
        assertEquals(pais.getId(), resultado.getId());
        assertEquals(pais.getNombre(), resultado.getNombre());
        assertEquals(pais.getJugador().getColor(), resultado.getColor());
        assertEquals(pais.getEjercito(), resultado.getEjercito());
        assertEquals(pais.getContinente().getNombre(), resultado.getContinente());
    }

    @Test
    void testToDTOConContinenteNull() {
        pais.setContinente(null);

        paisDTO resultado = PaisMapper.toDTO(pais);

        assertNotNull(resultado);
        assertEquals(pais.getId(), resultado.getId());
        assertEquals(pais.getNombre(), resultado.getNombre());
        assertNull(resultado.getContinente());
    }


    @Test
    void testToDTOSinJugador() {
        pais.setJugador(null);
        assertThrows(NullPointerException.class, () -> {
            PaisMapper.toDTO(pais);
        });
    }
}