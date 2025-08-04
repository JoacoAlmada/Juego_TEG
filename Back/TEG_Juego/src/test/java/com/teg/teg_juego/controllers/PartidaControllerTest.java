package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.PartidaService;
import com.teg.teg_juego.model.DTO.jugadorDTO;
import com.teg.teg_juego.model.DTO.partidaDTO;
import com.teg.teg_juego.model.entities.Partida;
import com.teg.teg_juego.model.entities.ResultadoAtaque;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PartidaController.class)
public class PartidaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartidaService partidaService;

    @Test
    void TestObtenerTodasLasPartidas() throws Exception {
        when(partidaService.getAllPartidas()).thenReturn(List.of(new partidaDTO(), new partidaDTO()));

        mockMvc.perform(get("/api/Partida"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void TestObtenerPorId() throws Exception {
        partidaDTO dto = new partidaDTO();
        when(partidaService.getPartidaById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/Partida/1"))
                .andExpect(status().isOk());
    }

    @Test
    void TestCrearPartida() throws Exception {
        when(partidaService.crearPartida(any(Partida.class))).thenReturn(1);

        mockMvc.perform(post("/api/Partida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void TestActualizarPartida() throws Exception {
        when(partidaService.actualizarPartida(eq(1), any(Partida.class))).thenReturn(new partidaDTO());

        mockMvc.perform(put("/api/Partida/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void TestAvanzarFase() throws Exception {
        mockMvc.perform(put("/api/Partida/1/fase"))
                .andExpect(status().isOk());
        verify(partidaService).avanzarFase(1);
    }

    @Test
    void TestPasarTurno() throws Exception {
        mockMvc.perform(put("/api/Partida/1/turno"))
                .andExpect(status().isOk());
        verify(partidaService).pasarTurno(1);
    }

    @Test
    void TestGuardarPartida() throws Exception {
        mockMvc.perform(put("/api/Partida/1/guardar"))
                .andExpect(status().isOk());
        verify(partidaService).guardarPartida(1);
    }

    @Test
    void TestCargarPartida() throws Exception {
        when(partidaService.cargarPartida(1)).thenReturn(new partidaDTO());

        mockMvc.perform(get("/api/Partida/1/cargar"))
                .andExpect(status().isOk());
    }

    @Test
    void TestIniciarPartida() throws Exception {
        mockMvc.perform(post("/api/Partida/1/iniciar"))
                .andExpect(status().isOk());
        verify(partidaService).iniciarPartida(1);
    }

    @Test
    void TestColocar() throws Exception {
        mockMvc.perform(post("/api/Partida/1/colocar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"pais\":\"Chile\",\"tropas\":3}"))
                .andExpect(status().isOk());
        verify(partidaService).colocar(eq(1), eq("Chile"), eq(3));
    }

    @Test
    void TestAtacar() throws Exception {
        ResultadoAtaque resultado = new ResultadoAtaque();
        when(partidaService.atacar(eq(1), eq("Chile"), eq("Peru"))).thenReturn(resultado);

        mockMvc.perform(post("/api/Partida/1/atacar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"origen\":\"Chile\",\"destino\":\"Peru\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void TestMover() throws Exception {
        mockMvc.perform(post("/api/Partida/1/mover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"origen\":\"Chile\",\"destino\":\"Argentina\",\"tropas\":2}"))
                .andExpect(status().isOk());
        verify(partidaService).mover(eq(1), eq("Chile"), eq("Argentina"), eq(2));
    }

    @Test
    void TestIniciarRonda() throws Exception {
        mockMvc.perform(put("/api/Partida/1/ronda"))
                .andExpect(status().isOk());
        verify(partidaService).iniciarRonda(1);
    }

    @Test
    void TestObtenerGanadorConContenido() throws Exception {
        when(partidaService.obtenerGanador(1)).thenReturn(new jugadorDTO());

        mockMvc.perform(get("/api/Partida/partidas/1/ganador"))
                .andExpect(status().isOk());
    }

    @Test
    void TestObtenerGanadorSinContenido() throws Exception {
        when(partidaService.obtenerGanador(1)).thenReturn(null);

        mockMvc.perform(get("/api/Partida/partidas/1/ganador"))
                .andExpect(status().isNoContent());
    }
}