package com.teg.teg_juego.model.DTO;

import com.teg.teg_juego.model.enums.Simbolo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class tarjetaDTOTest {

    @Test
    void testConstructorVacioYSetters() {
        tarjetaDTO dto = new tarjetaDTO();
        dto.setId(1);
        dto.setSimbolo(Simbolo.INFANTERIA);
        dto.setPais("Argentina");

        assertEquals(Integer.valueOf(1), dto.getId());
        assertEquals(Simbolo.INFANTERIA, dto.getSimbolo());
        assertEquals("Argentina", dto.getPais());
    }

    @Test
    void testConstructorConArgumentos() {
        tarjetaDTO dto = new tarjetaDTO(2, Simbolo.CABALLERIA, "Brasil");

        assertEquals(Integer.valueOf(2), dto.getId());
        assertEquals(Simbolo.CABALLERIA, dto.getSimbolo());
        assertEquals("Brasil", dto.getPais());
    }

    @Test
    void testEqualsAndHashCode() {
        tarjetaDTO dto1 = new tarjetaDTO(3, Simbolo.ARTILLERIA, "Chile");
        tarjetaDTO dto2 = new tarjetaDTO(3, Simbolo.ARTILLERIA, "Chile");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        tarjetaDTO dto = new tarjetaDTO(4, Simbolo.CABALLERIA, "Perú");
        String result = dto.toString();

        assertTrue(result.contains("4"));
        assertTrue(result.contains("CABALLERIA"));
        assertTrue(result.contains("Perú"));
    }

    @Test
    void testNotEquals() {
        tarjetaDTO dto1 = new tarjetaDTO(5, Simbolo.CABALLERIA, "Uruguay");
        tarjetaDTO dto2 = new tarjetaDTO(6, Simbolo.ARTILLERIA, "Bolivia");

        assertNotEquals(dto1, dto2);
    }
}