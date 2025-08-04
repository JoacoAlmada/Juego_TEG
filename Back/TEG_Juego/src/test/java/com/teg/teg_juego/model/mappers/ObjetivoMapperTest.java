package com.teg.teg_juego.model.mappers;


import com.teg.teg_juego.model.DTO.objetivoDTO;
import com.teg.teg_juego.model.entities.Objetivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjetivoMapperTest {

    private Objetivo objetivo;

    @BeforeEach
    void setUp() {
        objetivo = new Objetivo();
        objetivo.setId(1);
        objetivo.setDescripcion("Conquistar 24 territorios");
    }

    @Test
    void testToDTOCompleto() {
        objetivoDTO resultado = ObjetivoMapper.toDTO(objetivo);

        assertNotNull(resultado);
        assertEquals(objetivo.getId(), resultado.getId());
        assertEquals(objetivo.getDescripcion(), resultado.getDescripcion());
    }

    @Test
    void testToDTOConObjetivoNull() {
        objetivoDTO resultado = ObjetivoMapper.toDTO(null);
        assertNull(resultado);
    }

    @Test
    void testToDTOConDescripcionNull() {
        objetivo.setDescripcion(null);

        objetivoDTO resultado = ObjetivoMapper.toDTO(objetivo);

        assertNotNull(resultado);
        assertEquals(objetivo.getId(), resultado.getId());
        assertNull(resultado.getDescripcion());
    }
}