package com.teg.teg_juego.model.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class moverDTOTest {

    @Test
    public void testConstructorSinParametrosYSetters() {
        // Arrange & Act
        moverDTO dto = new moverDTO();

        // Datos de prueba
        String origen = "Argentina";
        String destino = "Brasil";
        Integer tropas = 5;

        // Setear valores
        dto.setOrigen(origen);
        dto.setDestino(destino);
        dto.setTropas(tropas);

        // Assert
        assertEquals(origen, dto.getOrigen());
        assertEquals(destino, dto.getDestino());
        assertEquals(tropas, dto.getTropas());
    }

    @Test
    public void testConstructorConParametros() {
        // Arrange
        String origen = "Chile";
        String destino = "Peru";
        Integer tropas = 10;

        // Act
        moverDTO dto = new moverDTO(origen, destino, tropas);

        // Assert
        assertEquals(origen, dto.getOrigen());
        assertEquals(destino, dto.getDestino());
        assertEquals(tropas, dto.getTropas());
    }


    @Test
    public void testConstructorConParametrosNull() {
        // Act
        moverDTO dto = new moverDTO(null, null, null);

        // Assert
        assertNull(dto.getOrigen());
        assertNull(dto.getDestino());
        assertNull(dto.getTropas());
    }

    @Test
    public void testStringsVacios() {
        // Arrange
        moverDTO dto = new moverDTO();

        // Act
        dto.setOrigen("");
        dto.setDestino("");
        dto.setTropas(0);

        // Assert
        assertEquals("", dto.getOrigen());
        assertEquals("", dto.getDestino());
        assertEquals(0, dto.getTropas());
        assertTrue(dto.getOrigen().isEmpty());
        assertTrue(dto.getDestino().isEmpty());
    }



    @Test
    public void testConstructorConValoresMixtos() {
        // Test con origen null
        moverDTO dto1 = new moverDTO(null, "Destino", 5);
        assertNull(dto1.getOrigen());
        assertEquals("Destino", dto1.getDestino());
        assertEquals(5, dto1.getTropas());

        // Test con destino null
        moverDTO dto2 = new moverDTO("Origen", null, 10);
        assertEquals("Origen", dto2.getOrigen());
        assertNull(dto2.getDestino());
        assertEquals(10, dto2.getTropas());

        // Test con tropas null
        moverDTO dto3 = new moverDTO("Origen", "Destino", null);
        assertEquals("Origen", dto3.getOrigen());
        assertEquals("Destino", dto3.getDestino());
        assertNull(dto3.getTropas());
    }

}