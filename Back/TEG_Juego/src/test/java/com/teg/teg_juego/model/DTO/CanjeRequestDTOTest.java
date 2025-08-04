package com.teg.teg_juego.model.DTO;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CanjeRequestDTOTest {

    @Test
    void testConstructorVacioYSetters() {
        CanjeRequestDTO dto = new CanjeRequestDTO();
        dto.setNumeroCanje(123);
        List<Integer> tarjetas = Arrays.asList(1, 2, 3);
        dto.setIdsTarjetasSeleccionadas(tarjetas);

        assertEquals(123, dto.getNumeroCanje());
        assertEquals(tarjetas, dto.getIdsTarjetasSeleccionadas());
    }

    @Test
    void testConstructorConArgumentos() {
        List<Integer> tarjetas = Arrays.asList(4, 5, 6);
        CanjeRequestDTO dto = new CanjeRequestDTO(456, tarjetas);

        assertEquals(456, dto.getNumeroCanje());
        assertEquals(tarjetas, dto.getIdsTarjetasSeleccionadas());
    }

    @Test
    void testEqualsAndHashCode() {
        List<Integer> tarjetas1 = Arrays.asList(7, 8, 9);
        List<Integer> tarjetas2 = Arrays.asList(7, 8, 9);

        CanjeRequestDTO dto1 = new CanjeRequestDTO(789, tarjetas1);
        CanjeRequestDTO dto2 = new CanjeRequestDTO(789, tarjetas2);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        List<Integer> tarjetas = Arrays.asList(10, 11, 12);
        CanjeRequestDTO dto = new CanjeRequestDTO(321, tarjetas);
        String result = dto.toString();

        assertTrue(result.contains("321"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("11"));
        assertTrue(result.contains("12"));
    }
}