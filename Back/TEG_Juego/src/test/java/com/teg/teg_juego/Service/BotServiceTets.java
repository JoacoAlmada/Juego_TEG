package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.botRepository;
import com.teg.teg_juego.Repository.jugadorRepository;
import com.teg.teg_juego.Repository.partidaRepository;
import com.teg.teg_juego.model.entities.Bot;
import com.teg.teg_juego.model.entities.Partida;
import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.DificultadBot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BotServiceTets {
    @Mock
    private partidaRepository partidaRepository;

    @Mock
    private botRepository botRepository;

    @Mock
    private jugadorRepository jugadorRepository;

    @InjectMocks
    private BotService botService;





    @Test
    void testAgregarBotsAlaPartida_agregaCorrectamente() {
        Partida partida = new Partida();
        partida.setJugadores(new ArrayList<>());

        Bot bot1 = new Bot();
        Bot bot2 = new Bot();

        List<Bot> botsDisponibles = List.of(bot1, bot2);
        List<Color> colores = List.of(Color.ROJO, Color.AZUL);

        when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));
        when(botRepository.findByDificultad(DificultadBot.NOVATO)).thenReturn(botsDisponibles);

        botService.agregarBotsAlaPartida(1, DificultadBot.NOVATO, 2, colores);

        assertEquals(2, partida.getJugadores().size());
        assertEquals(Color.ROJO, bot1.getColor());
        assertEquals(Color.AZUL, bot2.getColor());

        verify(jugadorRepository).saveAll(List.of(bot1, bot2));
        verify(partidaRepository).save(partida);
    }

    @Test
    void testAgregarBotsAlaPartida_lanzaErrorSiNoHaySuficientesBots() {
        Partida partida = new Partida();
        when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));
        when(botRepository.findByDificultad(DificultadBot.NOVATO)).thenReturn(List.of(new Bot()));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                botService.agregarBotsAlaPartida(1, DificultadBot.NOVATO, 2, List.of(Color.ROJO, Color.AZUL)));

        assertTrue(ex.getMessage().contains("No hay suficientes bots"));
    }
}
