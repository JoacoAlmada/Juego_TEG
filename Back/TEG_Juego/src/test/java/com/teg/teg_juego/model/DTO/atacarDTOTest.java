package com.teg.teg_juego.model.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class atacarDTOTest {

    @Test
    void testSettersAndGetters() {
        atacarDTO atacarDTO = new atacarDTO();

        atacarDTO.setOrigen("Argentina");
        atacarDTO.setDestino("Brasil");

        assertEquals("Argentina", atacarDTO.getOrigen());
        assertEquals("Brasil", atacarDTO.getDestino());
    }

    @Test
    public void testToString() {
        atacarDTO dto = new atacarDTO();
        dto.setOrigen("Chile");
        dto.setDestino("Uruguay");

        String output = dto.toString();
        assertEquals(true, output.contains("Chile"));
        assertEquals(true, output.contains("Uruguay"));
    }
}