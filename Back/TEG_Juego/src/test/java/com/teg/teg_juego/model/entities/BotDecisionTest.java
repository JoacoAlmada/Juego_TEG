package com.teg.teg_juego.model.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.DificultadBot;
import com.teg.teg_juego.model.enums.EstadoPartida;
import com.teg.teg_juego.model.enums.Fase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class BotDecisionTest {
    private Bot bot;
    private Pais pais1, pais2, pais3;
    private Partida partida;

    @BeforeEach
    void setUp() {
        // Inicializar países, vecinos, continente
        pais1 = new Pais();
        pais1.setNombre("Argentina");
        pais2 = new Pais();
        pais2.setNombre("Brasil");
        pais3 = new Pais();
        pais3.setNombre("Chile");

        pais1.agregarVecino(pais2);
        pais2.agregarVecino(pais1);
        pais2.agregarVecino(pais3);
        pais3.agregarVecino(pais2);

        pais1.setJugador(null);
        pais2.setJugador(null);
        pais3.setJugador(null);

        Continente continenteSudamerica = new Continente();
        continenteSudamerica.setNombre("Sudamérica");

        pais1.setContinente(continenteSudamerica);
        pais2.setContinente(continenteSudamerica);
        pais3.setContinente(continenteSudamerica);

        partida = new Partida();
        partida.setId(1);
        partida.setJugadores(new ArrayList<>());
        partida.setEstadoPartida(EstadoPartida.EN_JUEGO);
    }

    private Bot crearBotConColocacionForzada(String nombre, Color color, DificultadBot dificultad) {
        return new Bot(nombre, color, dificultad) {
            public void colocar(Pais pais, int cantidad) {
                pais.setEjercito(pais.getEjercito() + cantidad);
            }
        };
    }

    @Test
    void testDecisionNovatoFaseColocacion() {
        bot = new Bot("Bot_Novato", Color.ROJO, DificultadBot.NOVATO);

        // Setear tropas disponibles manualmente para el test (importante)
        bot.setFichasJ(10); // o la variable que guarda fichas para colocar

        bot.getPaises().add(pais1);
        pais1.setJugador(bot);
        pais1.setEjercito(3);

        partida.getJugadores().add(bot);
        partida.setTurno(0);
        partida.setRonda(2);
        partida.setFase(Fase.COLOCACION);
        bot.setPartida(partida);

        int ejercitoAntes = pais1.getEjercito();

        bot.tomarDecision();

        assertTrue(pais1.getEjercito() > ejercitoAntes, "Debe haber colocado fichas");
        assertEquals(Fase.ATAQUE, partida.getFase(), "La fase debería avanzar a ATAQUE");
    }

    @Test
    void testReagrupamientoSimple() {
        bot = crearBotConColocacionForzada("Bot_Reagrupe", Color.NEGRO, DificultadBot.EXPERTO);

        pais1.setJugador(bot);
        pais1.setEjercito(5);
        pais2.setJugador(bot);
        pais2.setEjercito(1);

        pais1.agregarVecino(pais2);
        pais2.agregarVecino(pais1);

        bot.getPaises().add(pais1);
        bot.getPaises().add(pais2);

        partida.getJugadores().add(bot);
        partida.setTurno(0);
        partida.setRonda(3);
        partida.setFase(Fase.REAGRUPACION);
        bot.setPartida(partida);

        int ejercitoOrigenAntes = pais1.getEjercito();
        int ejercitoDestinoAntes = pais2.getEjercito();

        bot.tomarDecision();

        assertTrue(pais1.getEjercito() < ejercitoOrigenAntes, "Debe haber movido tropas desde origen");
        assertTrue(pais2.getEjercito() > ejercitoDestinoAntes, "Debe haber recibido tropas el destino");
    }

    // Similar para Intermedio y Experto: setear fase antes de llamar a tomarDecision()
    // Test para canje de tarjetas sigue igual (se puede hacer en fase colocacion)
    // Test para fin de partida también, pero se debe simular la fase actual

    // Por ejemplo, test para fin de partida:

}