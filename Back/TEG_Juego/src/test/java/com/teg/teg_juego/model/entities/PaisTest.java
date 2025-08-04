
package com.teg.teg_juego.model.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaisTest {
    @Test
    void getVecinos() {
        Pais pais1 = new Pais();
        Pais pais2 = new Pais();
        pais2.setNombre("Vecino 1");

        PaisesVecinos relacion = new PaisesVecinos();
        relacion.setPaisVecino(pais2);

        List<PaisesVecinos> listaVecinos = new ArrayList<>();
        listaVecinos.add(relacion);

        pais1 = new Pais(1, "Argentina", null, null, 0, listaVecinos);

        List<Pais> vecinos = pais1.getVecinos();

        assertEquals(1, vecinos.size());
        assertEquals("Vecino 1", vecinos.get(0).getNombre());
    }

    @Test
    void getId() {
        Pais pais = new Pais();
        pais.setId(10);
        assertEquals(10, pais.getId());
    }

    @Test
    void setId() {
        Pais pais = new Pais();
        pais.setId(5);
        assertEquals(5, pais.getId());
    }

    @Test
    void getNombre() {
        Pais pais = new Pais();
        pais.setNombre("Brasil");
        assertEquals("Brasil", pais.getNombre());
    }

    @Test
    void setNombre() {
        Pais pais = new Pais();
        pais.setNombre("Chile");
        assertEquals("Chile", pais.getNombre());
    }

    @Test
    void getContinente() {
        Continente continente = new Continente();
        Pais pais = new Pais();
        pais.setContinente(continente);
        assertEquals(continente, pais.getContinente());
    }

    @Test
    void setContinente() {
        Continente continente = new Continente();
        Pais pais = new Pais();
        pais.setContinente(continente);
        assertEquals(continente, pais.getContinente());
    }

    @Test
    void getJugador() {
        Jugador jugador = new Jugador();
        Pais pais = new Pais();
        pais.setJugador(jugador);
        assertEquals(jugador, pais.getJugador());
    }

    @Test
    void setJugador() {
        Jugador jugador = new Jugador();
        Pais pais = new Pais();
        pais.setJugador(jugador);
        assertEquals(jugador, pais.getJugador());
    }

    @Test
    void getEjercito() {
        Pais pais = new Pais();
        pais.setEjercito(15);
        assertEquals(15, pais.getEjercito());
    }

    @Test
    void setEjercito() {
        Pais pais = new Pais();
        pais.setEjercito(20);
        assertEquals(20, pais.getEjercito());
    }

    @Test
    void getAndSetPartida() {
        Partida partida = new Partida();
        Pais pais = new Pais();

        pais.setPartida(partida);

        assertEquals(partida, pais.getPartida());
    }
}
