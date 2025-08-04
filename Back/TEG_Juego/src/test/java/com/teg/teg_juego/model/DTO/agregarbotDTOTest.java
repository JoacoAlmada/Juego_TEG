package com.teg.teg_juego.model.DTO;


import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.DificultadBot;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class agregarbotDTOTest {

    @Test
    void testConstructorConArgs() {
        List<Color> colores = Arrays.asList(Color.ROJO, Color.VERDE);
        agregarbotDTO botDTO = new agregarbotDTO(DificultadBot.EXPERTO, 2, colores);

        assertEquals(DificultadBot.EXPERTO, botDTO.getDificultad());
        assertEquals(2, botDTO.getCantidad());
        assertEquals(colores, botDTO.getColores());
    }

    @Test
    public void testConstructorVacioYSetters() {
        agregarbotDTO dto = new agregarbotDTO();

        dto.setDificultad(DificultadBot.NOVATO);
        dto.setCantidad(5);
        List<Color> colores = Arrays.asList(Color.AZUL, Color.MAGENTA);
        dto.setColores(colores);

        assertEquals(DificultadBot.NOVATO, dto.getDificultad());
        assertEquals(5, dto.getCantidad());
        assertEquals(colores, dto.getColores());
    }

    @Test
    public void testToString() {
        agregarbotDTO dto = new agregarbotDTO();
        dto.setDificultad(DificultadBot.INTERMEDIO);
        dto.setCantidad(2);
        dto.setColores(Arrays.asList(Color.ROJO, Color.VERDE));

        String result = dto.toString();
        assertTrue(result.contains("INTERMEDIO"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("ROJO"));
    }
}