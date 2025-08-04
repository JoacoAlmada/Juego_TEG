package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.jugadorRepository;
import com.teg.teg_juego.Repository.partidaRepository;
import com.teg.teg_juego.model.DTO.jugadorDTO;
import com.teg.teg_juego.model.DTO.jugadorPartidaDTO;
import com.teg.teg_juego.model.entities.Jugador;
import com.teg.teg_juego.model.entities.Partida;
import com.teg.teg_juego.model.enums.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JugadorServiceTest {
    @InjectMocks
    private JugadorService jugadorService;

    @Mock
    private jugadorRepository JugadorRepository;

    @Mock
    private partidaRepository partidaRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void testGetAllJugadores() {
        Jugador jugador1 = new Jugador();
        jugador1.setId(1);
        jugador1.setNombreJ("Carlitos");

        Jugador jugador2 = new Jugador();
        jugador2.setId(2);
        jugador2.setNombreJ("Tercero");

        when(JugadorRepository.findAll()).thenReturn(List.of(jugador1, jugador2));

        List<jugadorDTO> resultado = jugadorService.getAllJugadores();

        assertEquals(2, resultado.size());
        assertEquals("Carlitos", resultado.get(0).getNombre());
    }

    @Test
    void testGetJugadorById() {
        Jugador jugador = new Jugador();
        jugador.setId(1);
        jugador.setNombreJ("Carlitos");

        when(JugadorRepository.findById(1)).thenReturn(java.util.Optional.of(jugador));

        jugadorDTO dto = jugadorService.getJugadorById(1);

        assertEquals("Carlitos", dto.getNombre());
    }



    @Test
    void testCreateJugador() {
        jugadorDTO dto = new jugadorDTO();
        dto.setNombre("NuevoJugador");
        dto.setFichas(5);

        Jugador jugadorGuardado = new Jugador();
        jugadorGuardado.setId(1);
        jugadorGuardado.setNombreJ("NuevoJugador");
        jugadorGuardado.setFichasJ(5);

        when(JugadorRepository.save(any())).thenReturn(jugadorGuardado);
        when(modelMapper.map(any(Jugador.class), eq(jugadorDTO.class)))
                .thenReturn(new jugadorDTO(jugadorGuardado)); // o new jugadorDTO("Carlitos", Color.ROJO, 5)

        jugadorDTO result = jugadorService.createJugador(dto);

        assertEquals("NuevoJugador", result.getNombre());
        assertEquals(5, result.getFichas());
    }

    @Test
    void testUpdateJugador() {
        Jugador existente = new Jugador();
        existente.setId(1);
        existente.setNombreJ("ViejoNombre");

        Jugador actualizado = new Jugador();
        actualizado.setId(1);
        actualizado.setNombreJ("NombreActualizado");

        when(JugadorRepository.findById(1)).thenReturn(java.util.Optional.of(existente));
        when(JugadorRepository.save(any())).thenReturn(actualizado);
        when(modelMapper.map(any(Jugador.class), eq(jugadorDTO.class)))
                .thenReturn(new jugadorDTO(actualizado)); // o new jugadorDTO("Carlitos", Color.ROJO, 5)

        jugadorDTO result = jugadorService.updateJugador(1, actualizado);

        assertEquals("NombreActualizado", result.getNombre());
    }

    @Test
    void testDeleteJugador() {
        jugadorService.deleteJugador(1);
        verify(JugadorRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetJugadoresDePartida() {
        Jugador jugador = new Jugador();
        jugador.setId(1);
        jugador.setNombreJ("Carlitos");

        Partida partida = new Partida();
        partida.setId(1);
        partida.setJugadores(List.of(jugador));

        when(partidaRepository.findById(1)).thenReturn(java.util.Optional.of(partida));
        when(modelMapper.map(any(Jugador.class), eq(jugadorDTO.class)))
                .thenReturn(new jugadorDTO(jugador));


        List<jugadorDTO> jugadores = jugadorService.getJugadoresDePartida(1);

        assertEquals(1, jugadores.size());
        assertEquals("Carlitos", jugadores.get(0).getNombre());
    }

    @Test
    void testAgregarJugadorAPartida() {
        Partida partida = new Partida();
        partida.setId(1);
        partida.setJugadores(new java.util.ArrayList<>());

        Jugador jugador = new Jugador();
        jugador.setNombreJ("NuevoJugador");

        when(partidaRepository.findById(1)).thenReturn(java.util.Optional.of(partida));
        when(JugadorRepository.save(any())).thenAnswer(invocation -> {
            Jugador j = invocation.getArgument(0);
            j.setId(99); // Simula que fue guardado
            return j;
        });

        jugadorDTO result = jugadorService.agregarJugadorAPartida(1, jugador);

        assertEquals("NuevoJugador", result.getNombre());
        assertEquals(1, partida.getJugadores().size()); // Se añadió correctamente
    }

    @Test
    void testCrearJugadorEnPartida() {
        jugadorDTO jugador1 = new jugadorDTO();
        jugador1.setNombre("Carlitos");
        jugador1.setColor(Color.ROJO);

        jugadorDTO jugador2 = new jugadorDTO();
        jugador2.setNombre("Pepito");
        jugador2.setColor(Color.AZUL);

        jugadorPartidaDTO jugadorPartidaDTO = new jugadorPartidaDTO();
        jugadorPartidaDTO.setPartidaId(1);
        jugadorPartidaDTO.setJugadores(List.of(jugador1, jugador2));

        Partida partida = new Partida();
        partida.setId(1);

        when(partidaRepository.findById(1)).thenReturn(java.util.Optional.of(partida));

        jugadorService.crearJugadorEnPartida(jugadorPartidaDTO);

        verify(JugadorRepository, times(2)).save(any(Jugador.class));
    }

}