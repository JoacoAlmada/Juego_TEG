package com.teg.teg_juego.controllers;



import com.teg.teg_juego.Service.ObjetivoService;
import com.teg.teg_juego.model.DTO.objetivoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ObjetivoController.class)
public class ObjetivoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObjetivoService objetivoService;

    @Test
    void TestObtenerTodosLosObjetivos() throws Exception {
        objetivoDTO objetivo1 = new objetivoDTO();
        objetivo1.setId(1);
        objetivo1.setDescripcion("Conquistar 3 continentes");

        objetivoDTO objetivo2 = new objetivoDTO();
        objetivo2.setId(2);
        objetivo2.setDescripcion("Eliminar al jugador verde");

        when(objetivoService.getAllObjetivos()).thenReturn(List.of(objetivo1, objetivo2));

        mockMvc.perform(get("/api/Objetivo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].descripcion").value("Conquistar 3 continentes"))
                .andExpect(jsonPath("$[1].descripcion").value("Eliminar al jugador verde"));
    }

    @Test
    void TestObtenerPorId() throws Exception {
        objetivoDTO objetivo = new objetivoDTO();
        objetivo.setId(1);
        objetivo.setDescripcion("Conquistar Asia");

        when(objetivoService.getObjetivoById(1)).thenReturn(objetivo);

        mockMvc.perform(get("/api/Objetivo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Conquistar Asia"));
    }
}
