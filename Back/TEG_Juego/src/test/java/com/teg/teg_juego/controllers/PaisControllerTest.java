package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.PaisService;
import com.teg.teg_juego.model.DTO.paisDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaisControllerTest {

    @InjectMocks
    private PaisController paisController;

    @Mock
    private PaisService paisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        paisDTO pais1 = new paisDTO();
        pais1.setNombre("Argentina");

        paisDTO pais2 = new paisDTO();
        pais2.setNombre("Brasil");

        when(paisService.getAll()).thenReturn(List.of(pais1, pais2));

        List<paisDTO> resultado = paisController.getAll();

        assertEquals(2, resultado.size());
        assertEquals("Argentina", resultado.get(0).getNombre());
        assertEquals("Brasil", resultado.get(1).getNombre());
    }

    @Test
    void getById() {
        paisDTO pais = new paisDTO();
        pais.setNombre("Chile");

        when(paisService.getById(3)).thenReturn(pais);

        paisDTO resultado = paisController.getById(3);

        assertEquals("Chile", resultado.getNombre());
    }

    @Test
    void getVecinosPorNombre() {
        paisDTO vecino1 = new paisDTO();
        vecino1.setNombre("Uruguay");

        paisDTO vecino2 = new paisDTO();
        vecino2.setNombre("Paraguay");

        when(paisService.getVecinosPorNombre("Argentina")).thenReturn(List.of(vecino1, vecino2));

        List<paisDTO> vecinos = paisController.getVecinosPorNombre("Argentina");

        assertEquals(2, vecinos.size());
        assertEquals("Uruguay", vecinos.get(0).getNombre());
    }

    @Test
    void getTropasPorNombre() {
        when(paisService.getTropasPorNombre("Brasil")).thenReturn(7);

        Integer tropas = paisController.getTropasPorNombre("Brasil");

        assertEquals(7, tropas);
    }
}
