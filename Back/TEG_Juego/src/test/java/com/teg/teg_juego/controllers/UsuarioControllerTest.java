package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.UsuarioService;
import com.teg.teg_juego.model.DTO.loginDTO;
import com.teg.teg_juego.model.DTO.usuarioDTO;
import com.teg.teg_juego.model.entities.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void listarTodos() throws Exception {
        usuarioDTO usuario = new usuarioDTO(1, "TegUser", "1234", 1);

        List<usuarioDTO> listaUsuarios = List.of(usuario);
        Mockito.when(usuarioService.getAll()).thenReturn(listaUsuarios);

        mockMvc.perform(get("/api/Usuario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("TegUser"))
                .andExpect(jsonPath("$[0].contrasenia").value("1234"));
    }

    @Test
    void obtenerPorId() throws Exception {
        usuarioDTO usuario = new usuarioDTO(1, "TegUser", "1234", 1);

        Mockito.when(usuarioService.getById(1)).thenReturn(usuario);

        mockMvc.perform(get("/api/Usuario/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("TegUser"))
                .andExpect(jsonPath("$.contrasenia").value("1234"))
                .andExpect(jsonPath("$.nivel").value(1));
    }

    @Test
    void crear() throws Exception {
        loginDTO login = new loginDTO("TegUser", "1234");

        usuarioDTO creado = new usuarioDTO(1, "TegUser", "1234", 1);

        Mockito.when(usuarioService.create(Mockito.any(loginDTO.class))).thenReturn(creado);

        String jsonBody = """
            {
                "nombre": "TegUser",
                "contrasenia": "1234"
            }
            """;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/Usuario/register");
        mockMvc.perform(request
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("TegUser"))
                .andExpect(jsonPath("$.contrasenia").value("1234"))
                .andExpect(jsonPath("$.nivel").value(1));
    }

    @Test
    void actualizar() throws Exception {
        usuarioDTO respuesta = new usuarioDTO(1, "TegUserMod", "abcd", 2);

        Mockito.when(usuarioService.update(Mockito.eq(1), Mockito.any(Usuario.class)))
                .thenReturn(respuesta);

        String jsonBody = """
            {
                "id": 1,
                "nombre": "TegUserMod",
                "contrasenia": "abcd",
                "nivel": 2
            }
            """;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/Usuario/1");
        mockMvc.perform(request
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("TegUserMod"))
                .andExpect(jsonPath("$.contrasenia").value("abcd"))
                .andExpect(jsonPath("$.nivel").value(2));
    }

    @Test
    void eliminar() throws Exception {
        int idUsuario = 1;
        Mockito.doNothing().when(usuarioService).delete(idUsuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/Usuario/1");
        mockMvc.perform(request)
                .andExpect(status().isOk());

        Mockito.verify(usuarioService, times(1)).delete(idUsuario);
    }

    @Test
    void login() throws Exception {
        usuarioDTO respuesta = new usuarioDTO(1, "TegUser", "1234", 1);

        Mockito.when(usuarioService.login(Mockito.any(loginDTO.class)))
                .thenReturn(respuesta);

        String jsonBody = """
        {
            "nombre": "TegUser",
            "contrasenia": "1234"
        }
    """;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/Usuario/login");
        mockMvc.perform(request
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("TegUser"))
                .andExpect(jsonPath("$.contrasenia").value("1234"))
                .andExpect(jsonPath("$.nivel").value(1));
    }
}