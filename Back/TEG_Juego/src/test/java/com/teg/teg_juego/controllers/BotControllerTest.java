package com.teg.teg_juego.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.teg.teg_juego.Service.BotService;
import com.teg.teg_juego.model.DTO.agregarbotDTO;
import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.DificultadBot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BotController.class)
public class BotControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BotService botService;

    @Test
    void testEjecutarTurnosBots_retornaOk() throws Exception {
        int idPartida = 1;

        mockMvc.perform(post("/api/Bot/turno/{idPartida}", idPartida))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"mensaje\":\"Turnos de bots ejecutados hasta llegar a jugador humano o fin de partida.\"}"));

        verify(botService).ejecutarTurnosBots(idPartida);
    }

    @Test
    void testAgregarBots_retornaOk() throws Exception {
        int idPartida = 2;

        agregarbotDTO dto = new agregarbotDTO();
        dto.setDificultad(DificultadBot.NOVATO);
        dto.setCantidad(2);
        dto.setColores(List.of(Color.ROJO, Color.AZUL));

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/api/Bot/agregar/{idPartida}", idPartida)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Bots agregados correctamente a la partida."));

        verify(botService).agregarBotsAlaPartida(idPartida, dto.getDificultad(), dto.getCantidad(), dto.getColores());
    }
}
