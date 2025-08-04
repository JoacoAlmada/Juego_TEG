package com.teg.teg_juego.model.DTO;

import com.teg.teg_juego.model.enums.TipoObjetivo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class objetivoDTOTest {

    @Test
    void testConstructorVacioYSetters() {
        objetivoDTO dto = new objetivoDTO();
        dto.setId(1);
        dto.setDescripcion("Conquistar Asia");
        dto.setTipo(TipoObjetivo.CONQUISTA);
        dto.setEstado(true);

        assertEquals(1, dto.getId());
        assertEquals("Conquistar Asia", dto.getDescripcion());
        assertEquals(TipoObjetivo.CONQUISTA, dto.getTipo());
        assertTrue(dto.getEstado());
    }

    @Test
    void testConstructorConArgumentos() {
        objetivoDTO dto = new objetivoDTO(2, "Eliminar jugador", TipoObjetivo.ELIMINAR, false);

        assertEquals(2, dto.getId());
        assertEquals("Eliminar jugador", dto.getDescripcion());
        assertEquals(TipoObjetivo.ELIMINAR, dto.getTipo());
        assertFalse(dto.getEstado());
    }

    @Test
    void testEqualsAndHashCode() {
        objetivoDTO dto1 = new objetivoDTO(3, "Objetivo A", TipoObjetivo.CONQUISTA, true);
        objetivoDTO dto2 = new objetivoDTO(3, "Objetivo A", TipoObjetivo.CONQUISTA, true);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        objetivoDTO dto = new objetivoDTO(4, "Objetivo B", TipoObjetivo.ELIMINAR, false);
        String result = dto.toString();

        assertTrue(result.contains("4"));
        assertTrue(result.contains("Objetivo B"));
        assertTrue(result.contains("ELIMINAR"));
        assertTrue(result.contains("false"));
    }

    @Test
    void testNotEquals() {
        objetivoDTO dto1 = new objetivoDTO(5, "Objetivo C", TipoObjetivo.CONQUISTA, true);
        objetivoDTO dto2 = new objetivoDTO(6, "Objetivo D", TipoObjetivo.ELIMINAR, false);

        assertNotEquals(dto1, dto2);
    }
}