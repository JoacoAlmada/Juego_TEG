package com.teg.teg_juego.model.DTO;

import com.teg.teg_juego.model.enums.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class paisDTOTest {



    @Test
    void testToString() {
        paisDTO dto = new paisDTO(4, "Perú", Color.AMARILLO, 7, "América del Sur");
        String result = dto.toString();

        assertTrue(result.contains("4"));
        assertTrue(result.contains("Perú"));
        assertTrue(result.contains("AMARILLO")); // el toString incluye esto
        assertTrue(result.contains("7"));
        assertTrue(result.contains("América del Sur"));
    }

    @Test
    void testNotEquals() {
        paisDTO dto1 = new paisDTO(5, "Uruguay", Color.MAGENTA, 2, "América del Sur");
        paisDTO dto2 = new paisDTO(6, "Bolivia", Color.NEGRO, 3, "América del Sur");

        assertNotEquals(dto1, dto2);
    }
}