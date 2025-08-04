package com.teg.teg_juego.model.entities;

import com.teg.teg_juego.model.entities.RealizarAccion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RealizarAccionTest {

    @Test
    public void realizarAccion() {
        RealizarAccion realizarAccion = new RealizarAccion();
        realizarAccion.setTropas(5);
        realizarAccion.setPaisId(1);
        realizarAccion.setPais2Id(2);

        RealizarAccion realizarAccion1 = new RealizarAccion();
        realizarAccion1.setTropas(5);
        realizarAccion1.setPaisId(1);
        realizarAccion1.setPais2Id(2);

        assertEquals(realizarAccion.getTropas(), realizarAccion1.getTropas());
        assertEquals(realizarAccion.getPaisId(), realizarAccion1.getPaisId());
        assertEquals(realizarAccion.getPais2Id(), realizarAccion1.getPais2Id());
        assertEquals(realizarAccion.hashCode(), realizarAccion.hashCode());
        assertNotNull(realizarAccion.toString());
    }
}