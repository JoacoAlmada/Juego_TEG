
package com.teg.teg_juego.model.entities;


import com.teg.teg_juego.model.enums.EstadoPartida;
import com.teg.teg_juego.model.enums.Fase;
import com.teg.teg_juego.model.enums.TipoObjetivo;
import org.hibernate.mapping.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class PartidaTest {

    private Partida partida;
    private Jugador jugador1;
    private Jugador jugador2;
    List<Pais> paises = new ArrayList<Pais>();
    List<Objetivo> obj = new ArrayList<Objetivo>();

    @BeforeEach
    void setUp() {
        partida = new Partida(5, EstadoPartida.EN_JUEGO,4,3, Fase.ATAQUE,paises,obj);

    }


    //test que verifica que se cree el ctor de partida
    @Test
    void partidaConstructor(){


        Partida parti = new Partida(5,EstadoPartida.TERMINADA,4,3, Fase.ATAQUE,paises,obj);

        assertEquals(EstadoPartida.TERMINADA, parti.getEstadoPartida());
        assertEquals(4, parti.getCantidadJugadores());
        assertEquals(3, parti.getRonda());
        assertEquals(Fase.ATAQUE, parti.getFase());

    }



    //inicia la partida en null y llama al metodo iniciarPartida() que cambia el null a Enjuego
    @Test
    void iniciarPartida() {

        Partida p = new Partida();
        System.out.println(p.getEstadoPartida());

        assertNull(p.getEstadoPartida());

        p.iniciarPartida();

        assertEquals(EstadoPartida.EN_JUEGO, p.getEstadoPartida());
        assertNotEquals(EstadoPartida.TERMINADA,p.getEstadoPartida());
    }


    @Test
    void testPasarFase_DeColocacionAAtaque() {
        partida.setFase(Fase.COLOCACION);

        partida.pasarFase();

        assertEquals(Fase.ATAQUE, partida.getFase());
    }

    @Test
    void testPasarFase_DeAtaqueAReagrupacion() {
        partida.setFase(Fase.ATAQUE);

        partida.pasarFase();

        assertEquals(Fase.REAGRUPACION, partida.getFase());
    }

    @Test
    void getJugadorActualId() {
        Partida partida = new Partida();
        Jugador jugador = new Jugador();
        jugador.setId(10);
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);
        partida.setJugadores(jugadores);
        partida.setTurno(0);

        assertEquals(10, partida.getJugadorActualId());
    }

    @Test
    void getJugadorActualNombre() {
        Partida partida = new Partida();
        Jugador jugador = new Jugador();
        jugador.setNombreJ("Carlitos");
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);
        partida.setJugadores(jugadores);
        partida.setTurno(0);

        assertEquals("Carlitos", partida.getJugadorActualNombre());
    }

    @Test
    void getId() {
        Partida partida = new Partida();
        partida.setId(1);
        assertEquals(1, partida.getId());
    }


    @Test
    void getCantidadJugadores() {
        Partida partida = new Partida();
        partida.setCantidadJugadores(3);
        assertEquals(3, partida.getCantidadJugadores());
    }

    @Test
    void getRonda() {
        Partida partida = new Partida();
        partida.setRonda(5);
        assertEquals(5, partida.getRonda());
    }

    @Test
    void getTurno() {
        Partida partida = new Partida();
        partida.setTurno(2);
        assertEquals(2, partida.getTurno());
    }

    @Test
    void getFase() {
        Partida partida = new Partida();
        partida.setFase(Fase.ATAQUE);
        assertEquals(Fase.ATAQUE, partida.getFase());
    }

    @Test
    void getJugadores() {
        Partida partida = new Partida();
        List<Jugador> jugadores = new ArrayList<>();
        partida.setJugadores(jugadores);
        assertEquals(jugadores, partida.getJugadores());
    }

    @Test
    void getObjetivos() {
        Partida partida = new Partida();
        List<Objetivo> objetivos = new ArrayList<>();
        partida.setObjetivos(objetivos);
        assertEquals(objetivos, partida.getObjetivos());
    }


    @Test
    void guardarPartida_cambiaEstadoAGuardada() {
        partida.guardarPartida();

        assertEquals(EstadoPartida.GUARDADA, partida.getEstadoPartida(), "El estado de la partida debería ser GUARDADA");
    }

    @Test
    void terminarPartida_cambiaEstadoATerminadaYDevuelveJugador() {
        Jugador jugador = new Jugador(); // Usá tu implementación real
        Jugador resultado = partida.terminarPartida(jugador);

        assertEquals(EstadoPartida.TERMINADA, partida.getEstadoPartida(), "El estado de la partida debería ser TERMINADA");
        assertEquals(jugador, resultado, "Debería devolver el mismo jugador pasado como parámetro");
    }

    @Test
    void estaTerminada_devuelveTrueSiEstadoEsTerminada() {
        partida.terminarPartida(new Jugador());

        assertTrue(partida.estaTerminada(), "Debería devolver true si la partida está terminada");
    }

    @Test
    void estaTerminada_devuelveFalseSiEstadoNoEsTerminada() {
        partida.guardarPartida();

        assertFalse(partida.estaTerminada(), "Debería devolver false si la partida no está terminada");
    }

    @Test
    void testRepartirObjetivos_AsignaObjetivosCorrectamente() {

        Partida partida = new Partida();

        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        partida.setJugadores(List.of(jugador1, jugador2));

        Objetivo obj1 = new Objetivo(1, "Conquistar", false, TipoObjetivo.CONQUISTA);
        Objetivo obj2 = new Objetivo(2, "Eliminar", false, TipoObjetivo.ELIMINAR);

        partida.repartirObjetivos(List.of(obj1, obj2));

        assertNotNull(jugador1.getObjetivo());
        assertNotNull(jugador2.getObjetivo());
    }

    @Test
    void testRepartirObjetivos_LanzaExcepcionSinJugadores() {
        Partida partida = new Partida();

        Exception ex = assertThrows(RuntimeException.class, () ->
                partida.repartirObjetivos(List.of(new Objetivo()))
        );
        assertEquals("No hay jugadores en la partida", ex.getMessage());
    }

    @Test
    void testRepartirObjetivos_LanzaExcepcionSinObjetivos() {
        Partida partida = new Partida();

        partida.setJugadores(List.of(new Jugador()));

        Exception ex = assertThrows(RuntimeException.class, () ->
                partida.repartirObjetivos(Collections.emptyList())
        );
        assertEquals("No hay objetivos disponibles", ex.getMessage());
    }

    @Test
    void testRepartirPaises_DistribuyeCorrectamente() {
        Partida partida = new Partida();

        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        jugador1.setPaises(new ArrayList<>());
        jugador2.setPaises(new ArrayList<>());

        partida.setJugadores(List.of(jugador1, jugador2));

        Pais pais1 = new Pais();
        Pais pais2 = new Pais();
        Pais pais3 = new Pais();

        List<Pais> paises = List.of(pais1, pais2, pais3);

        partida.repartirPaises(paises);

        // Verificamos que todos los países tengan 1 ejército y un jugador asignado
        for (Pais pais : paises) {
            assertEquals(1, pais.getEjercito(), "El país " + pais.getNombre() + " no tiene 1 ejército");
            assertNotNull(pais.getJugador(), "El país " + pais.getNombre() + " no tiene jugador asignado");
        }

        // Verificamos que entre todos los jugadores se hayan repartido los países
        int totalPaisesAsignados = jugador1.getPaises().size() + jugador2.getPaises().size();
        assertEquals(3, totalPaisesAsignados);
    }

    @Test
    void testRepartirPaises_LanzaExcepcionSinJugadores() {
        Partida partida = new Partida();

        Exception ex = assertThrows(RuntimeException.class, () ->
                partida.repartirPaises(List.of(new Pais()))
        );
        assertEquals("No hay jugadores en la partida", ex.getMessage());
    }

    @Test
    void testRepartirPaises_LanzaExcepcionSinPaises() {
        Partida partida = new Partida();
        partida.setJugadores(List.of(new Jugador()));

        Exception ex = assertThrows(RuntimeException.class, () ->
                partida.repartirPaises(Collections.emptyList())
        );
        assertEquals("No hay países disponibles", ex.getMessage());
    }

    @Test
    void testPasarTurno_CambiaTurnoYFase() {
        Partida partida = new Partida();

        // Setup de jugadores
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        partida.setJugadores(List.of(jugador1, jugador2));

        // Inicializar ronda en 0 en lugar de null
        partida.setTurno(0);
        partida.setRonda(0); // Cambiar esto

        // Ejecutamos el método
        partida.pasarTurno();

        // Verificamos que la ronda fue incrementada
        assertEquals(0, partida.getRonda());
        assertEquals(1, partida.getTurno());
        assertEquals(Fase.COLOCACION, partida.getFase());
    }

    @Test
    void testPasarTurno_CambiaRondaAlVolverAlJugador0() {
        Partida partida = new Partida();

        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        partida.setJugadores(List.of(jugador1, jugador2));

        partida.setTurno(1); // Estamos en el segundo jugador
        partida.setRonda(1);

        partida.pasarTurno(); // Debería volver al jugador 0 y subir la ronda a 2

        assertEquals(0, partida.getTurno());
        assertEquals(2, partida.getRonda());
        assertEquals(Fase.COLOCACION, partida.getFase());
    }
}
