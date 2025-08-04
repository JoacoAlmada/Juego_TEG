package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.jugadorRepository;
import com.teg.teg_juego.Repository.tarjetaPaisRepository;
import com.teg.teg_juego.model.DTO.tarjetaDTO;
import com.teg.teg_juego.model.entities.Jugador;
import com.teg.teg_juego.model.entities.TarjetaPais;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.modelmapper.ModelMapper;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarjetaServiceTest {

    @InjectMocks
    private TarjetaService tarjetaService;

    @Mock
    private tarjetaPaisRepository tarjetaRepository;

    @Mock
    private jugadorRepository jugadorRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void TestObtenerTodasLasTarjetas() {
        TarjetaPais tarjeta1 = new TarjetaPais();
        tarjeta1.setNumber(1);

        TarjetaPais tarjeta2 = new TarjetaPais();
        tarjeta2.setNumber(2);

        tarjetaDTO dto1 = new tarjetaDTO();
        tarjetaDTO dto2 = new tarjetaDTO();

        when(tarjetaRepository.findAll()).thenReturn(List.of(tarjeta1, tarjeta2));
        when(modelMapper.map(tarjeta1, tarjetaDTO.class)).thenReturn(dto1);
        when(modelMapper.map(tarjeta2, tarjetaDTO.class)).thenReturn(dto2);

        List<tarjetaDTO> resultado = tarjetaService.getAll();

        assertEquals(2, resultado.size());
        verify(modelMapper, times(2)).map(any(TarjetaPais.class), eq(tarjetaDTO.class));
    }

    @Test
    void TestObtenerTarjetasDeJugador() {
        Jugador jugador = new Jugador();

        TarjetaPais tarjeta1 = new TarjetaPais();
        tarjeta1.setNumber(1);

        TarjetaPais tarjeta2 = new TarjetaPais();
        tarjeta2.setNumber(2);

        jugador.setTarjetas(List.of(tarjeta1, tarjeta2));

        tarjetaDTO dto1 = new tarjetaDTO();
        tarjetaDTO dto2 = new tarjetaDTO();

        when(jugadorRepository.findById(1)).thenReturn(Optional.of(jugador));
        when(modelMapper.map(tarjeta1, tarjetaDTO.class)).thenReturn(dto1);
        when(modelMapper.map(tarjeta2, tarjetaDTO.class)).thenReturn(dto2);

        List<tarjetaDTO> resultado = tarjetaService.getTarjetasJugador(1);
        assertEquals(2, resultado.size());
        verify(modelMapper, times(2)).map(any(TarjetaPais.class), eq(tarjetaDTO.class));
    }
    @Test
    void TestJugadorNoEncontrado() {
        when(jugadorRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            tarjetaService.getTarjetasJugador(99);
        });
        assertEquals("Jugador no encontrado", ex.getMessage());
    }
}
