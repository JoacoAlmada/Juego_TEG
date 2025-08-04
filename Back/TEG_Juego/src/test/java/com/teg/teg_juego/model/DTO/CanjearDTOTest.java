package com.teg.teg_juego.model.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanjearDTOTest {

    @Test
    void testConstructorVacioYSetters() {
        CanjearDTO dto = new CanjearDTO();
        dto.setExito(true);
        dto.setTropasObtenidas(10);
        dto.setMensaje("Canje exitoso");

        assertTrue(dto.isExito());
        assertEquals(10, dto.getTropasObtenidas());
        assertEquals("Canje exitoso", dto.getMensaje());
    }

    @Test
    void testConstructorConArgumentos() {
        CanjearDTO dto = new CanjearDTO(true, 20, "Canje realizado");

        assertTrue(dto.isExito());
        assertEquals(20, dto.getTropasObtenidas());
        assertEquals("Canje realizado", dto.getMensaje());
    }

    @Test
    void testEqualsAndHashCode() {
        CanjearDTO dto1 = new CanjearDTO(true, 30, "OK");
        CanjearDTO dto2 = new CanjearDTO(true, 30, "OK");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        CanjearDTO dto = new CanjearDTO(false, 0, "Error");
        String result = dto.toString();

        assertTrue(result.contains("false"));
        assertTrue(result.contains("0"));
        assertTrue(result.contains("Error"));
    }

    @Test
    void testNotEquals() {
        CanjearDTO dto1 = new CanjearDTO(true, 10, "Mensaje A");
        CanjearDTO dto2 = new CanjearDTO(false, 10, "Mensaje A");

        assertNotEquals(dto1, dto2);
    }
}