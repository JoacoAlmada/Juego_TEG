package com.teg.teg_juego.model.entities;


import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContinenteTest {

    @Test
    void constructorAsignaCorrectamenteLosValores() {
        // Arrange
        Integer id = 1;
        String nombre = "Europa";
        Integer cantidadPaises = 9;
        boolean conquistado = true;

        // Act
        Continente continente = new Continente(id, nombre, cantidadPaises, conquistado);

        // Assert
        assertEquals(id, continente.getId());
        assertEquals(nombre, continente.getNombre());
        assertEquals(cantidadPaises, continente.getCantidad_paises());
        assertTrue(continente.isConquistado());
    }

    @Test
    void settersYGettersFuncionanCorrectamente() {
        // Arrange
        Continente continente = new Continente();

        // Act + Assert con el setter/getter primitivo (que escribiste vos)
        continente.setConquistado(false);
        assertFalse(continente.isConquistado());

        // Act + Assert con el setter/getter de lombok (Boolean)
        continente.setConquistado(Boolean.TRUE);
        assertEquals(Boolean.TRUE, continente.getConquistado());

        // Otros setters y getters
        continente.setId(2);
        continente.setNombre("Asia");
        continente.setCantidad_paises(15);

        assertEquals(2, continente.getId());
        assertEquals("Asia", continente.getNombre());
        assertEquals(15, continente.getCantidad_paises());
    }

    @Test
    void paises_contienteTieneLosContinentesCorrectos() {
        Map<String, List<String>> mapa = Continente.paises_contiente;

        assertTrue(mapa.containsKey("AmericaSur"));
        assertTrue(mapa.containsKey("Asia"));
        assertEquals(List.of("Argentina", "Brasil", "Uruguay", "Chile", "Colombia", "Peru"), mapa.get("AmericaSur"));
        assertEquals(6, mapa.get("Africa").size());
    }

    @Test
    void listaDePaisesSeInicializaVacia() {
        Continente continente = new Continente();
        assertNotNull(continente.getPaises());
        assertTrue(continente.getPaises().isEmpty());
    }
}