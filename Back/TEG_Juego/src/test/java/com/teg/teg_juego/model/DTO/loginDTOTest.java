package com.teg.teg_juego.model.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class loginDTOTest {

    @Test
    public void testNoArgsConstructorAndSetters() {
        loginDTO dto = new loginDTO();
        dto.setNombre("usuario");
        dto.setContrasenia("clave123");

        assertEquals("usuario", dto.getNombre());
        assertEquals("clave123", dto.getContrasenia());
    }

    @Test
    public void testAllArgsConstructor() {
        loginDTO dto = new loginDTO("admin", "admin123");

        assertEquals("admin", dto.getNombre());
        assertEquals("admin123", dto.getContrasenia());
    }

    @Test
    public void testToString() {
        loginDTO dto = new loginDTO("guest", "1234");

        String result = dto.toString();

        assertTrue(result.contains("guest"));
        assertTrue(result.contains("1234"));
    }

    @Test
    public void testEqualsAndHashCode() {
        loginDTO dto1 = new loginDTO("user1", "pass1");
        loginDTO dto2 = new loginDTO("user1", "pass1");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}