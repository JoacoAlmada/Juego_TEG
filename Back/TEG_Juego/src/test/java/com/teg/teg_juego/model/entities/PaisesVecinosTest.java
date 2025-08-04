package com.teg.teg_juego.model.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaisesVecinosTest {

    @Test
    void constructorAsignaCorrectamenteLosValores() {
        // Arrange
        Pais pais = new Pais();
        pais.setId(1);
        pais.setNombre("Argentina");

        Pais paisVecino = new Pais();
        paisVecino.setId(2);
        paisVecino.setNombre("Brasil");

        int id = 10;

        // Act
        PaisesVecinos relacion = new PaisesVecinos(id, pais, paisVecino);

        // Assert
        assertEquals(id, relacion.getId());
        assertEquals(pais, relacion.getPais());
        assertEquals(paisVecino, relacion.getPaisVecino());
    }

    @Test
    void settersYGettersFuncionanCorrectamente() {
        // Arrange
        PaisesVecinos relacion = new PaisesVecinos();

        Pais pais = new Pais();
        pais.setNombre("Chile");

        Pais paisVecino = new Pais();
        paisVecino.setNombre("Perú");

        // Act
        relacion.setId(5);
        relacion.setPais(pais);
        relacion.setPaisVecino(paisVecino);

        // Assert
        assertEquals(5, relacion.getId());
        assertEquals("Chile", relacion.getPais().getNombre());
        assertEquals("Perú", relacion.getPaisVecino().getNombre());
    }
}