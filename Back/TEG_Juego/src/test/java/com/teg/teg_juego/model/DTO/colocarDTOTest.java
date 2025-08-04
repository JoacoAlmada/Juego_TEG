package com.teg.teg_juego.model.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class colocarDTOTest {

    @Test
    public void testNoArgsConstructorAndSetters() {
        colocarDTO dto = new colocarDTO();

        dto.setPais("Argentina");
        dto.setTropas(5);

        assertEquals("Argentina", dto.getPais());
        assertEquals(5, dto.getTropas());
    }

    @Test
    public void testToString() {
        colocarDTO dto = new colocarDTO();
        dto.setPais("Brasil");
        dto.setTropas(10);

        String result = dto.toString();

        assertTrue(result.contains("Brasil"));
        assertTrue(result.contains("10"));
    }
}