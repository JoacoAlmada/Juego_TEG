package com.teg.teg_juego.model.mappers;


import com.teg.teg_juego.model.DTO.tarjetaDTO;
import com.teg.teg_juego.model.entities.Pais;
import com.teg.teg_juego.model.entities.TarjetaPais;
import com.teg.teg_juego.model.enums.Simbolo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TarjetaMapperTest {

    private TarjetaPais tarjetaPais;
    private Pais pais;

    @BeforeEach
    void setUp() {
        pais = new Pais();
        pais.setId(1);
        pais.setNombre("Argentina");

        tarjetaPais = new TarjetaPais();
        tarjetaPais.setNumber(1);
        tarjetaPais.setSimbolo(Simbolo.INFANTERIA);
        tarjetaPais.setPais(pais);
    }

    @Test
    void testToDTOCompleto() {
        tarjetaDTO resultado = TarjetaMapper.toDTO(tarjetaPais);

        assertNotNull(resultado);
        assertEquals(tarjetaPais.getNumber(), resultado.getId());
        assertEquals(tarjetaPais.getSimbolo(), resultado.getSimbolo());
        assertEquals(tarjetaPais.getPais().getNombre(), resultado.getPais());
    }

    @Test
    void testToDTOConPaisNull() {
        tarjetaPais.setPais(null);

        tarjetaDTO resultado = TarjetaMapper.toDTO(tarjetaPais);

        assertNotNull(resultado);
        assertEquals(tarjetaPais.getNumber(), resultado.getId());
        assertEquals(tarjetaPais.getSimbolo(), resultado.getSimbolo());
        assertNull(resultado.getPais());
    }

    @Test
    void testToDTOConTarjetaNull() {
        tarjetaDTO resultado = TarjetaMapper.toDTO(null);
        assertNull(resultado);
    }

}