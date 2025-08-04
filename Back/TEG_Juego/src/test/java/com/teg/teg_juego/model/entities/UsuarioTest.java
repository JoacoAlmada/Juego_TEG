
package com.teg.teg_juego.model.entities;
import com.teg.teg_juego.model.enums.TipoJugador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    void testConstructorConParametros() {
        Usuario usuario = new Usuario(1, "FrancoGhersi", "fofo123", 7, TipoJugador.HUMANO);

        assertEquals(1, usuario.getId());
        assertEquals("FrancoGhersi", usuario.getNombre());
        assertEquals("fofo123", usuario.getContrasenia());
        assertEquals(7, usuario.getNivel());
        assertEquals(TipoJugador.HUMANO, usuario.getIdTipoJugador());

        assertNotEquals("CarlitoxTercero", usuario.getNombre());
        assertNotEquals("123", usuario.getContrasenia());
        assertNotEquals(99, usuario.getNivel());
    }

    @Test
    void testConstructorVacioYSettersYGetters() {
        Usuario usuario = new Usuario();

        usuario.setId(2);
        usuario.setNombre("CarlitoxTercero");
        usuario.setContrasenia("claveSegura123");
        usuario.setNivel(10);
        usuario.setIdTipoJugador(TipoJugador.BOT);

        assertEquals(2, usuario.getId());
        assertEquals("CarlitoxTercero", usuario.getNombre());
        assertEquals("claveSegura123", usuario.getContrasenia());
        assertEquals(10, usuario.getNivel());
        assertEquals(TipoJugador.BOT, usuario.getIdTipoJugador());
    }
}

