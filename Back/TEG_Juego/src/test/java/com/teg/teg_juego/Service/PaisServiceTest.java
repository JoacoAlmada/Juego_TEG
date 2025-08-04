package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.paisRepository;
import com.teg.teg_juego.model.DTO.paisDTO;
import com.teg.teg_juego.model.entities.Jugador;
import com.teg.teg_juego.model.entities.Pais;
import com.teg.teg_juego.model.enums.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaisServiceTest {

    private PaisService paisService;
    private paisRepository PaisRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        PaisRepository = mock(paisRepository.class);
        modelMapper = new ModelMapper();

        paisService = new PaisService();
        paisService.PaisRepository = PaisRepository;
        paisService.modelMapper = modelMapper;
    }

    @Test
    void getAll() {
        // Creamos países simulados
        Pais argentina = new Pais();
        argentina.setId(1);
        argentina.setNombre("Argentina");
        argentina.setEjercito(10);

        Jugador jugador = new Jugador();
        jugador.setColor(Color.ROJO);

        argentina.setJugador(jugador);

        Pais chile = new Pais();
        chile.setId(2);
        chile.setNombre("Chile");
        chile.setEjercito(8);

        Jugador jugador1 = new Jugador();
        jugador.setColor(Color.ROJO);

        chile.setJugador(jugador1);

        when(PaisRepository.findAll()).thenReturn(List.of(argentina, chile));

        List<paisDTO> resultado = paisService.getAll();

        assertEquals(2, resultado.size());
        assertEquals("Argentina", resultado.get(0).getNombre());
        assertEquals("Chile", resultado.get(1).getNombre());
    }

    @Test
    void getById() {
        Pais brasil = new Pais();
        brasil.setId(3);
        brasil.setNombre("Brasil");
        brasil.setEjercito(12);

        Jugador jugador = new Jugador();
        jugador.setColor(Color.ROJO);

        brasil.setJugador(jugador);

        when(PaisRepository.findById(3)).thenReturn(Optional.of(brasil));

        paisDTO dto = paisService.getById(3);

        assertEquals("Brasil", dto.getNombre());
        assertEquals(12, dto.getEjercito());
    }

    @Test
    void getVecinosPorNombre() {
        Pais uruguay = new Pais();
        uruguay.setId(4);
        uruguay.setNombre("Uruguay");
        uruguay.setEjercito(4);

        Pais paraguay = new Pais();
        paraguay.setId(5);
        paraguay.setNombre("Paraguay");
        paraguay.setEjercito(6);

        // Relación de vecinos
        uruguay.agregarVecino(paraguay);

        when(PaisRepository.findByNombre("Uruguay")).thenReturn(Optional.of(uruguay));

        List<paisDTO> vecinos = paisService.getVecinosPorNombre("Uruguay");

        assertEquals(1, vecinos.size());
        assertEquals("Paraguay", vecinos.get(0).getNombre());
    }

    @Test
    void getTropasPorNombre() {
        Pais bolivia = new Pais();
        bolivia.setId(6);
        bolivia.setNombre("Bolivia");
        bolivia.setEjercito(7);

        when(PaisRepository.findByNombre("Bolivia")).thenReturn(Optional.of(bolivia));

        Integer tropas = paisService.getTropasPorNombre("Bolivia");

        assertEquals(7, tropas);
    }
}
