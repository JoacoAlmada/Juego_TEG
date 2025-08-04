package com.teg.teg_juego.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.teg.teg_juego.Service.TurnoService;
import com.teg.teg_juego.model.DTO.turnoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class TurnoControllerTest {

    @Mock
    private TurnoService turnoService;

    @InjectMocks
    private TurnoController turnoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(turnoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getTurnoActual_DeberiaRetornarTurnoActual_CuandoIdPartidaEsValido() throws Exception {
        Integer idPartida = 1;
        Integer turnoEsperado = 3;

        when(turnoService.getTurnoActual(idPartida)).thenReturn(turnoEsperado);

        mockMvc.perform(get("/api/Turno/{idPartida}", idPartida)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(turnoEsperado));
    }

    @Test
    void getTurnoActual_DeberiaRetornarTurnoCero_CuandoPartidaEsNueva() throws Exception {
        Integer idPartida = 2;
        Integer turnoEsperado = 0;

        when(turnoService.getTurnoActual(idPartida)).thenReturn(turnoEsperado);

        mockMvc.perform(get("/api/Turno/{idPartida}", idPartida)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(turnoEsperado));
    }

    @Test
    void getTurnoActual_DeberiaRetornarNumeroNegativo_CuandoPartidaNoExiste() throws Exception {
        Integer idPartida = 999;
        Integer turnoEsperado = -1;

        when(turnoService.getTurnoActual(idPartida)).thenReturn(turnoEsperado);

        mockMvc.perform(get("/api/Turno/{idPartida}", idPartida)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(turnoEsperado));
    }

    @Test
    void getHistorialTurnos_DeberiaRetornarHistorial_CuandoIdPartidaEsValido() throws Exception {
        Integer idPartida = 1;
        turnoDTO historialEsperado = new turnoDTO();

        when(turnoService.getHistorialTurnos(idPartida)).thenReturn(historialEsperado);

        mockMvc.perform(get("/api/Turno/{idPartida}/historial", idPartida)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getHistorialTurnos_DeberiaRetornarHistorialVacio_CuandoPartidaEsNueva() throws Exception {
        Integer idPartida = 2;
        turnoDTO historialVacio = new turnoDTO();

        when(turnoService.getHistorialTurnos(idPartida)).thenReturn(historialVacio);

        mockMvc.perform(get("/api/Turno/{idPartida}/historial", idPartida)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getHistorialTurnos_DeberiaRetornarNull_CuandoPartidaNoExiste() throws Exception {
        Integer idPartida = 999;

        when(turnoService.getHistorialTurnos(idPartida)).thenReturn(null);

        mockMvc.perform(get("/api/Turno/{idPartida}/historial", idPartida)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getTurnoActual_DeberiaFuncionar_ConIdPartidaGrande() throws Exception {
        // Arrange
        Integer idPartida = Integer.MAX_VALUE;
        Integer turnoEsperado = 5;

        when(turnoService.getTurnoActual(idPartida)).thenReturn(turnoEsperado);

        mockMvc.perform(get("/api/Turno/{idPartida}", idPartida)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(turnoEsperado));
    }

    @Test
    void getHistorialTurnos_DeberiaFuncionar_ConIdPartidaGrande() throws Exception {
        Integer idPartida = Integer.MAX_VALUE;
        turnoDTO historialEsperado = new turnoDTO();

        when(turnoService.getHistorialTurnos(idPartida)).thenReturn(historialEsperado);

        mockMvc.perform(get("/api/Turno/{idPartida}/historial", idPartida)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}