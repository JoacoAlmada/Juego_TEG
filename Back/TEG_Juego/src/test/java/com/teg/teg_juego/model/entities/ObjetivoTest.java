package com.teg.teg_juego.model.entities;


import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.TipoObjetivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ObjetivoTest {

    private Objetivo objetivo;

    @BeforeEach
    void setUp() {
        objetivo = new Objetivo();
    }
    private Jugador jugador;
    @BeforeEach
    void setup() {
        jugador = new Jugador() {
            @Override
            public boolean tieneContinenteConquistado(String nombreContinente) {
                // Simula que tiene conquistado cualquier continente que le pidas
                return true;
            }

            @Override
            public boolean tienePaisesPorContinente(String nombreContinente, Integer cantidad) {
                // Simula que tiene suficientes países en cualquier continente
                return true;
            }
        };
    }

    @Test
    void testObjetivo1() {
        jugador.setObjetivo(new Objetivo(1, "Ocupar Africa, 5 Paises de America del Norte, 4 Paises de Europa", false, TipoObjetivo.CONQUISTA));
        assertTrue(jugador.getObjetivo().verificarPaisesObjetivos(jugador));
    }

    @Test
    void testObjetivo2() {
        jugador.setObjetivo(new Objetivo(1, "Ocupar Asia, 2 Paises de America del Sur", false, TipoObjetivo.CONQUISTA));
        assertTrue(jugador.getObjetivo().verificarPaisesObjetivos(jugador));
    }

    @Test
    void testObjetivo3() {
        jugador.setObjetivo(new Objetivo(1, "Ocupar Europa, 4 Paises de Asia, 2 Paises de America del Sur", false, TipoObjetivo.CONQUISTA));
        assertTrue(jugador.getObjetivo().verificarPaisesObjetivos(jugador));
    }

    @Test
    void testObjetivo4() {
        jugador.setObjetivo(new Objetivo(1, "Ocupar America del Norte, 2 Paises de Oceania y 4 Paises de Asia", false, TipoObjetivo.CONQUISTA));
        assertTrue(jugador.getObjetivo().verificarPaisesObjetivos(jugador));
    }

    @Test
    void testObjetivo5() {
        jugador.setObjetivo(new Objetivo(1, "Ocupar 2 Paises de Oceania, 2 Paises de Africa, 2 Paises de America del Sur, 4 Paises de America del Norte, 3 Paises de Europa y 3 Paises de Asia", false, TipoObjetivo.CONQUISTA));
        assertTrue(jugador.getObjetivo().verificarPaisesObjetivos(jugador));
    }

    @Test
    void testObjetivo6() {
        jugador.setObjetivo(new Objetivo(1, "Ocupar Oceania, America del Norte, 2 Paises de Africa", false, TipoObjetivo.CONQUISTA));
        assertTrue(jugador.getObjetivo().verificarPaisesObjetivos(jugador));
    }

    @Test
    void testObjetivo7() {
        jugador.setObjetivo(new Objetivo(1, "Ocupar America del Sur, Ocupar Africa, 4 Paises de Asia", false, TipoObjetivo.CONQUISTA));
        assertTrue(jugador.getObjetivo().verificarPaisesObjetivos(jugador));
    }

    @Test
    void testObjetivo8() {
        jugador.setObjetivo(new Objetivo(1, "Ocupar Oceania, Ocupar Africa, 5 Paises de America del Norte", false, TipoObjetivo.CONQUISTA));
        assertTrue(jugador.getObjetivo().verificarPaisesObjetivos(jugador));
    }

    @Test
    void testConstructorYGetters() {
        Objetivo obj = new Objetivo(1, "Destruir Totalmente al Ejercito Rojo", false, TipoObjetivo.ELIMINAR);
        assertEquals(1, obj.getId());
        assertEquals("Destruir Totalmente al Ejercito Rojo", obj.getDescripcion());
        assertFalse(obj.getEstado());
        assertEquals(TipoObjetivo.ELIMINAR, obj.getTipo());
    }

    @Test
    void testSettersYGetters() {
        objetivo.setId(2);
        objetivo.setDescripcion("Conquistar África");
        objetivo.setEstado(false);
        objetivo.setTipo(TipoObjetivo.CONQUISTA);

        assertEquals(2, objetivo.getId());
        assertEquals("Conquistar África", objetivo.getDescripcion());
        assertFalse(objetivo.getEstado());
        assertEquals(TipoObjetivo.CONQUISTA, objetivo.getTipo());
    }

    @Test
    void testObtenerColorDesdeDescripcion() {
        assertEquals(Color.NEGRO, objetivo.obtenerColor("Destruir Totalmente al Ejercito Negro"));
        assertEquals(Color.MAGENTA, objetivo.obtenerColor("Destruir Totalmente al Ejercito Magenta"));
        assertNull(objetivo.obtenerColor("Conquistar el mundo"));
    }
    @Test
    void testVerificarObjetivoEliminacion_Cumple() {
        objetivo.setDescripcion("Destruir Totalmente al Ejercito Azul");
        objetivo.setEstado(false);

        Jugador jugador = new Jugador();
        jugador.setColor(Color.ROJO);
        jugador.setObjetivo(objetivo);
        jugador.setColoresEliminados(Set.of(Color.AZUL));

        Jugador enemigo = new Jugador();
        enemigo.setColor(Color.AZUL);
        enemigo.setPaises(Collections.emptyList());


        boolean resultado = objetivo.verificarObjetivoEliminacion(jugador, List.of(enemigo));

        assertTrue(resultado);
        assertTrue(objetivo.getEstado());

    }



    @Test
    void testVerificarObjetivoEliminacion_NoCumple() {
        objetivo.setDescripcion("Destruir Totalmente al Ejercito Verde");
        objetivo.setEstado(false);

        Jugador jugador = new Jugador();
        jugador.setColor(Color.ROJO);
        jugador.setObjetivo(objetivo);
        jugador.setColoresEliminados(Collections.emptySet()); // mejor que null

        Jugador enemigo = new Jugador();
        enemigo.setColor(Color.VERDE);
        enemigo.setPaises(Collections.emptyList());

        boolean resultado = objetivo.verificarObjetivoEliminacion(jugador, List.of(enemigo));

        assertFalse(resultado);
        assertEquals("Conquistar 30 paises", jugador.getObjetivo().getDescripcion());
        assertFalse(objetivo.getEstado());
    }

    @Test
    public void cumpleVerificarPaisesObjetivos() {
        Jugador jugador = new Jugador();
        List<Pais> paises = new ArrayList<>();

        // Continente Africa (debe tenerlos todos)
        for (String nombre : Continente.paises_contiente.get("Africa")) {
            Pais pais = new Pais();
            pais.setNombre(nombre);
            paises.add(pais);
        }

        // 5 Países de America del Norte
        Pais pais1 = new Pais(); pais1.setNombre("Alaska"); paises.add(pais1);
        Pais pais2 = new Pais(); pais2.setNombre("Mexico"); paises.add(pais2);
        Pais pais3 = new Pais(); pais3.setNombre("Canada"); paises.add(pais3);
        Pais pais4 = new Pais(); pais4.setNombre("Oregon"); paises.add(pais4);
        Pais pais5 = new Pais(); pais5.setNombre("Terranova"); paises.add(pais5);

        // 4 Países de Europa
        Pais pais6 = new Pais(); pais6.setNombre("Alemania"); paises.add(pais6);
        Pais pais7 = new Pais(); pais7.setNombre("Francia"); paises.add(pais7);
        Pais pais8 = new Pais(); pais8.setNombre("Italia"); paises.add(pais8);
        Pais pais9 = new Pais(); pais9.setNombre("Polonia"); paises.add(pais9);

        jugador.setPaises(paises);

        objetivo.setDescripcion("Ocupar Africa, 5 Paises de America del Norte, 4 Paises de Europa");
        jugador.setObjetivo(objetivo);

        boolean resultado = objetivo.verificarPaisesObjetivos(jugador);

        assertTrue(resultado);
    }

    @Test
    void noCumpleVerificarPaisesObjetivos() {
        Jugador jugador = new Jugador();
        List<Pais> paises = new ArrayList<>();

        Pais pais6 = new Pais(); pais6.setNombre("Alemania"); paises.add(pais6);
        Pais pais7 = new Pais(); pais7.setNombre("Francia"); paises.add(pais7);
        Pais pais8 = new Pais(); pais8.setNombre("Italia"); paises.add(pais8);
        Pais pais9 = new Pais(); pais9.setNombre("Polonia"); paises.add(pais9);

        jugador.setPaises(paises);


        objetivo.setDescripcion("Ocupar Africa, 5 Paises de America del Norte, 4 Paises de Europa");
        jugador.setObjetivo(objetivo);

        // Act
        boolean resultado = objetivo.verificarPaisesObjetivos(jugador);

        // Assert
        assertFalse(resultado);

    }

    @Test
    void verificarObjetivoGeneral() {
        Jugador jugador = new Jugador();
        jugador.setObjetivo(objetivo);

        Partida partida = new Partida();
        partida.setRonda(3);
        jugador.setPartida(partida);

        List<Pais> paises = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            Pais pais = new Pais();
            pais.setNombre("Pais" + i);
            paises.add(pais);
        }
        jugador.setPaises(paises);

        boolean resultado = objetivo.verificarObjetivo(jugador, new ArrayList<>());

        assertTrue(resultado);
    }

    @Test
    void verificarObjetivoConquista(){
        Objetivo objetivo = new Objetivo();
        objetivo.setTipo(TipoObjetivo.CONQUISTA);
        objetivo.setDescripcion("Ocupar Asia, 2 Paises de America del Sur");

        Jugador jugador = new Jugador();
        jugador.setObjetivo(objetivo);

        Partida partida = new Partida();
        partida.setRonda(3);
        jugador.setPartida(partida);

        List<Pais> paises = new ArrayList<>();

        for (String nombre : Continente.paises_contiente.get("Asia")) {
            Pais pais = new Pais();
            pais.setNombre(nombre);
            paises.add(pais);
        }
        Pais pais1 = new Pais(); pais1.setNombre("Argentina"); paises.add(pais1);
        Pais pais2 = new Pais(); pais2.setNombre("Chile"); paises.add(pais2);

        jugador.setPaises(paises);

        boolean resultado = objetivo.verificarObjetivo(jugador, new ArrayList<>());

        assertTrue(resultado);
    }

    @Test
    void verificarObjetivoEliminacion(){
        objetivo.setTipo(TipoObjetivo.ELIMINAR);
        objetivo.setDescripcion("Destruir Totalmente al Ejercito Verde");

        Jugador jugador = new Jugador();
        jugador.setObjetivo(objetivo);

        Partida partida = new Partida();
        partida.setRonda(3);
        jugador.setPartida(partida);

        Set<Color> coloresEliminados = new HashSet<>();
        coloresEliminados.add(Color.VERDE);
        jugador.setColoresEliminados(coloresEliminados);

        Jugador enemigo = new Jugador();
        enemigo.setColor(Color.VERDE);

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);
        jugadores.add(enemigo);

        boolean resultado = objetivo.verificarObjetivo(jugador, jugadores);

        assertTrue(resultado);
    }


    @Test
    void testEsCumplido_estadoNull() {
        objetivo.esCumplido();
        assertTrue(objetivo.getEstado());
    }

    @Test
    void testEsCumplido_estadoFalse() {
        objetivo.setEstado(false);
        assertTrue(objetivo.esCumplido());
        assertTrue(objetivo.getEstado());
    }
}
