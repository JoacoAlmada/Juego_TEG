package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.TarjetaService;
import com.teg.teg_juego.model.DTO.tarjetaDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TarjetaController.class)
public class TarjetaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarjetaService tarjetaService;

    @Test
    void testGetAllTarjetas() throws Exception {
        tarjetaDTO tarjeta1 = new tarjetaDTO();
        tarjetaDTO tarjeta2 = new tarjetaDTO();

        when(tarjetaService.getAll()).thenReturn(List.of(tarjeta1, tarjeta2));

        mockMvc.perform(get("/api/Tarjeta"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetTarjetasDeJugador() throws Exception {
        tarjetaDTO tarjeta1 = new tarjetaDTO();
        tarjetaDTO tarjeta2 = new tarjetaDTO();

        when(tarjetaService.getTarjetasJugador(1)).thenReturn(List.of(tarjeta1, tarjeta2));

        mockMvc.perform(get("/api/Tarjeta/jugador/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }
}