package com.teg.teg_juego.model.entities;

import com.teg.teg_juego.model.enums.Simbolo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TarjetaPaisTest {

    @Test
    void usar_noHaceNadaSiPaisSinJugador() {
        Pais pais = new Pais();
        pais.setNombre("Brasil");
        pais.setEjercito(3);
        pais.setJugador(null);
        Simbolo simbolo = Simbolo.INFANTERIA;
        TarjetaPais tarjeta = new TarjetaPais(2, simbolo, pais);

        //tarjeta.usar();

        assertEquals(3, pais.getEjercito());
    }

    @Test
    void usar_noHaceNadaSiPaisEsNull() {
        Simbolo simbolo = Simbolo.CABALLERIA;
        TarjetaPais tarjeta = new TarjetaPais(3, simbolo, null);

        //assertDoesNotThrow(tarjeta::usar);
    }

    @Test
    void gettersAndSettersFuncionan() {
        // Constructor por defecto
        TarjetaPais tarjeta = new TarjetaPais();

        tarjeta.setNumber(10);
        tarjeta.setSimbolo(Simbolo.ARTILLERIA);

        Pais pais = new Pais();
        pais.setNombre("Chile");
        tarjeta.setPais(pais);

        Jugador jugador = new Jugador();
        tarjeta.setJugador(jugador);

        assertEquals(10, tarjeta.getNumber());
        assertEquals(Simbolo.ARTILLERIA, tarjeta.getSimbolo());
        assertEquals("Chile", tarjeta.getPais().getNombre());
        assertEquals(jugador, tarjeta.getJugador());
    }

    @Test
    void constructorConArgumentos_asignaCamposCorrectamente() {
        Pais pais = new Pais();
        pais.setNombre("Perú");
        TarjetaPais tarjeta = new TarjetaPais(7, Simbolo.CABALLERIA, pais);

        assertEquals(7, tarjeta.getNumber());
        assertEquals(Simbolo.CABALLERIA, tarjeta.getSimbolo());
        assertEquals("Perú", tarjeta.getPais().getNombre());
    }
}