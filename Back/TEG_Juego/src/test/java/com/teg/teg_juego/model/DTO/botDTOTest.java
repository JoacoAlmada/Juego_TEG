package com.teg.teg_juego.model.DTO;


import com.teg.teg_juego.model.entities.Bot;
import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.DificultadBot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class botDTOTest {

    @Test
    void testConstructorDeBot() {
        Bot bot = new Bot();
        bot.setId(1);
        bot.setNombreJ("Bot1");
        bot.setColor(Color.ROJO); // Suponiendo que tenés un enum Color
        bot.setDificultad(DificultadBot.EXPERTO);

        botDTO dto = new botDTO(bot);

        assertEquals(1, dto.getId());
        assertEquals("Bot1", dto.getNombre());
        assertEquals(Color.ROJO, dto.getColor());
        assertEquals(DificultadBot.EXPERTO, dto.getDificultad());
    }

    @Test
    void testGettersAndSettersBot() {
        botDTO dto = new botDTO();
        dto.setNombre("Bot2");
        dto.setColor(Color.VERDE);
        dto.setDificultad(DificultadBot.NOVATO);

        assertEquals("Bot2", dto.getNombre());
        assertEquals(Color.VERDE, dto.getColor());
        assertEquals(DificultadBot.NOVATO, dto.getDificultad());
    }

    @Test
    public void testConstructorVacioYSetters() {
        botDTO dto = new botDTO();

        dto.setId(2);
        dto.setNombre("Bot2");
        dto.setColor(Color.VERDE);
        dto.setDificultad(DificultadBot.NOVATO);

        assertEquals(2, dto.getId());
        assertEquals("Bot2", dto.getNombre());
        assertEquals(Color.VERDE, dto.getColor());
        assertEquals(DificultadBot.NOVATO, dto.getDificultad());
    }

    @Test
    public void testToString() {
        botDTO dto = new botDTO();
        dto.setId(3);
        dto.setDificultad(DificultadBot.INTERMEDIO);

        String result = dto.toString();
        assertTrue(result.contains("INTERMEDIO"));
    }

    @Test
    public void testAllArgsConstructor() {
        botDTO dto = new botDTO(DificultadBot.INTERMEDIO);

        assertNull(dto.getNombre());
        assertNull(dto.getColor());
        assertEquals(DificultadBot.INTERMEDIO, dto.getDificultad());
    }
}
