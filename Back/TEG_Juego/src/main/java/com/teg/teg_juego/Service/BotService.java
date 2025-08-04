package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.botRepository;
import com.teg.teg_juego.Repository.jugadorRepository;
import com.teg.teg_juego.Repository.partidaRepository;
import com.teg.teg_juego.model.entities.Bot;
import com.teg.teg_juego.model.entities.Jugador;
import com.teg.teg_juego.model.entities.Partida;
import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.DificultadBot;
import com.teg.teg_juego.model.enums.TipoJugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BotService {

    @Autowired
    private partidaRepository partidaRepository;

    @Autowired
    private botRepository botRepository;

    @Autowired
    private jugadorRepository jugadorRepository;

    @Autowired
    private PartidaService partidaService;


    public void ejecutarTurnosBots(int idPartida) {
        while (true) {
            Partida partida = partidaRepository.findById(idPartida)
                    .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

            if (partida.estaTerminada()) break;

            Jugador jugadorActual = partida.getJugadores().get(partida.getTurno());

            if (!(jugadorActual instanceof Bot bot)) break;

            System.out.println("[BotService] Ejecutando turno de bot: " + bot.getNombreJ());

            bot.tomarDecision();
            partidaRepository.save(partida);


            Partida partidaActualizada = partidaRepository.findById(idPartida)
                    .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

            Jugador nuevoJugador = partidaActualizada.getJugadores().get(partidaActualizada.getTurno());

            if (!(nuevoJugador instanceof Bot)) {
                System.out.println("[BotService] Turno ahora es de jugador humano, saliendo...");
                break;
            }
        }
    }


    public void agregarBotsAlaPartida(int idPartida, DificultadBot dificultad, int cantidad, List<Color> colores) {
        Partida partida = partidaRepository.findById(idPartida)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        List<Bot> botsDisponibles = botRepository.findByDificultad(dificultad)
                .stream()
                .toList();

        if (botsDisponibles.size() < cantidad) {
            throw new RuntimeException("No hay suficientes bots disponibles con dificultad: " + dificultad);
        }

        if (colores.size() < cantidad) {
            throw new RuntimeException("No hay suficientes colores para asignar a los bots");
        }

        List<Bot> botsAsignados = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            Bot bot = botsDisponibles.get(i);
            bot.setPartida(partida);
            bot.setTipoJ(TipoJugador.BOT);
            bot.setFichasJ(0);
            bot.setObjetivo(null);
            bot.setColor(colores.get(i));

            botsAsignados.add(bot);
            partida.getJugadores().add(bot);
        }
        partida.setCantidadJugadores(partida.getJugadores().size());


        Integer turno = partida.getTurno();
        if (turno == null || turno >= partida.getJugadores().size()) {
            partida.setTurno(0);
        }

        jugadorRepository.saveAll(botsAsignados);
        partidaRepository.save(partida);
    }
}