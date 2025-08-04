package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.ContinenteService;
import com.teg.teg_juego.model.entities.Continente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
class ContinenteControllerTest {

    private ContinenteService continenteService;
    private ContinenteController controller;

    @BeforeEach
    void setUp() {
        continenteService = mock(ContinenteService.class);
        controller = new ContinenteController();
        ReflectionTestUtils.setField(controller, "continenteService", continenteService);
    }

    @Test
    void testGetAll() {
        List<Continente> mockList = List.of(new Continente(1, "Europa", 3, false));
        when(continenteService.getAll()).thenReturn(mockList);

        List<Continente> resultado = controller.getAll();

        assertEquals(1, resultado.size());
        assertEquals("Europa", resultado.get(0).getNombre());
        verify(continenteService).getAll();
    }
}