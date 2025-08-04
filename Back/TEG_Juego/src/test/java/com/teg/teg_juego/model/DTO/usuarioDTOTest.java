package com.teg.teg_juego.model.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class usuarioDTOTest {

    @Test
    public void usuarioDTO_Test() {
        usuarioDTO usuarioDTO = new usuarioDTO();
        usuarioDTO.setId(1);
        usuarioDTO.setContrasenia("1234");
        usuarioDTO.setNombre("User");
        usuarioDTO.setNivel(1);

        usuarioDTO usuarioDTO1 = new usuarioDTO();
        usuarioDTO1.setId(1);
        usuarioDTO1.setContrasenia("1234");
        usuarioDTO1.setNombre("User");
        usuarioDTO1.setNivel(1);

        assertEquals(usuarioDTO.getId(), usuarioDTO1.getId());
        assertEquals(usuarioDTO.getContrasenia(), usuarioDTO1.getContrasenia());
        assertEquals(usuarioDTO.getNombre(), usuarioDTO1.getNombre());
        assertEquals(usuarioDTO.getNivel(), usuarioDTO1.getNivel());
        assertEquals(usuarioDTO.hashCode(), usuarioDTO1.hashCode());
        assertNotNull(usuarioDTO.toString());
    }
}