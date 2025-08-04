package com.teg.teg_juego.model.entities;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JugadorTest {

    @Test
    public void colocarFailTest() {
        Pais pais = new Pais();
        Jugador jugador = new Jugador();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jugador.colocar(pais,1);
        });

        assertEquals("Tropas insuficientes", exception.getMessage());
    }

    @Test
    public void atacarTresDosTest() {
        Pais origen = new Pais();
        origen.setEjercito(3);
        Pais destino = new Pais();
        destino.setEjercito(2);

        origen.setVecino(destino);

        Jugador atacante = new Jugador();
        atacante.setPaises(new ArrayList<>(List.of(origen)));

        Jugador defensor = new Jugador();
        defensor.setPaises(new ArrayList<>(List.of(destino)));

        destino.setJugador(defensor);

        Jugador jugadorSpy = Mockito.spy(atacante);
        Mockito.doReturn(6).when(jugadorSpy).tirarDado();

        ResultadoAtaque resultado = jugadorSpy.atacar(origen, destino);

        assertEquals("ataque", resultado.getResultado());
        assertEquals(1, origen.getEjercito());
    }

    @Test
    public void atacarDosUnoTest() {
        Pais origen = new Pais();
        origen.setEjercito(2);
        Pais destino = new Pais();
        destino.setEjercito(1);

        origen.setVecino(destino);

        Jugador atacante = new Jugador();
        atacante.setPaises(new ArrayList<>(List.of(origen)));

        Jugador defensor = new Jugador();
        defensor.setPaises(new ArrayList<>(List.of(destino)));

        destino.setJugador(defensor);

        Jugador jugadorSpy = Mockito.spy(atacante);
        Mockito.doReturn(6).when(jugadorSpy).tirarDado();

        ResultadoAtaque resultado = jugadorSpy.atacar(origen, destino);

        assertEquals("ataque", resultado.getResultado());
        assertEquals(1, origen.getEjercito());
    }

    @Test
    public void atacarNoLimitrofes() {
        Pais origen = new Pais();
        Pais destino = new Pais();
        Jugador jugador = new Jugador();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jugador.atacar(origen, destino);
        });

        assertEquals("Los paises deben ser limitrofes", exception.getMessage());
    }

    @Test
    public void atacarSinTropas() {
        Pais origen = new Pais();
        origen.setEjercito(1);
        Pais destino = new Pais();
        destino.setEjercito(2);
        Jugador jugador = new Jugador();

        origen.setVecino(destino);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jugador.atacar(origen, destino);
        });

        assertEquals("Tropas insuficientes para atacar", exception.getMessage());
    }

    @Test
    public void moverSinTropas() {
        Pais origen = new Pais();
        Pais destino = new Pais();
        Jugador jugador = new Jugador();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jugador.mover(origen, destino,1);
        });

        assertEquals("Tropas insuficientes", exception.getMessage());
    }

    @Test
    public void moverNoLimitrofes() {
        Pais origen = new Pais();
        Pais destino = new Pais();
        Jugador jugador = new Jugador();
        origen.setEjercito(2);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jugador.mover(origen, destino,1);
        });

        assertEquals("Los paises deben ser limitrofes", exception.getMessage());
    }

    @Test
    void paisesDeContinente() {
        List<Pais> paises = new ArrayList<>();
        List<Pais> paisesNo = new ArrayList<>();
        Continente continente1 = new Continente();
        continente1.setNombre("AmericaSur");

        Continente continente2 = new Continente();
        continente2.setNombre("AmericaNorte");

        Pais pais1 = new Pais();
        pais1.setNombre("Argentina");
        pais1.setContinente(continente1);

        Pais pais2 = new Pais();
        pais2.setNombre("Brasil");
        pais2.setContinente(continente1);

        Pais pais3 = new Pais();
        pais3.setNombre("Chile");
        pais3.setContinente(continente1);

        Pais pais4 = new Pais();
        pais4.setNombre("Estados Unidos");
        pais4.setContinente(continente2);

        paises.add(pais1);
        paises.add(pais2);
        paises.add(pais3);

        paisesNo.add(pais2);
        paisesNo.add(pais3);
        paisesNo.add(pais4);

        String continente = "AmericaSur";

        Jugador jugador = new Jugador();
        Jugador jugadorNo = new Jugador();
        Jugador sinPaises = new Jugador();
        jugador.setPaises(paises);
        jugadorNo.setPaises(paisesNo);
        sinPaises.setPaises(new ArrayList<>());

        boolean resultadoSi = jugador.tienePaisesPorContinente(continente,3);
        boolean resultadoNo = jugadorNo.tienePaisesPorContinente(continente,3);
        boolean resultadoSinPaises = sinPaises.tienePaisesPorContinente(continente,3);

        assertTrue(resultadoSi);
        assertFalse(resultadoNo);
        assertFalse(resultadoSinPaises);
    }
    @Test
    void tienePaisConNombre() {
        List<Pais> paises = new ArrayList<>();

        Pais pais1 = new Pais();
        pais1.setNombre("Argentina");
        Pais pais2 = new Pais();
        pais2.setNombre("Brasil");

        paises.add(pais1);
        paises.add(pais2);

        Jugador jugador = new Jugador();
        jugador.setPaises(paises);

        boolean resultadoSi =  jugador.tienePaisConNombre("Argentina");
        boolean resultadoNo = jugador.tienePaisConNombre("Estados Unidos");

        assertTrue(resultadoSi);
        assertFalse(resultadoNo);
    }

    @Test
    void tieneContinenteConquistado() {
        Jugador jugador = new Jugador();

        List<Pais> paises = new ArrayList<>();
        for (String nombre : Continente.paises_contiente.get("AmericaSur")) {
            Pais pais = new Pais();
            pais.setNombre(nombre);
            paises.add(pais);
        }
        jugador.setPaises(paises);

        boolean resultado = jugador.tieneContinenteConquistado("AmericaSur");

        assertTrue(resultado);
    }
    @Test
    void noTienteContinenteConquistado() {
        Jugador jugador = new Jugador();

        List<Pais> paises = new ArrayList<>();
        Pais pais1 = new Pais();
        pais1.setNombre("Argentina");
        paises.add(pais1);
        Pais  pais2 = new Pais();
        pais2.setNombre("Brasil");
        paises.add(pais2);
        // Falta el resto de países de AmericaSur

        jugador.setPaises(paises);

        boolean resultado = jugador.tieneContinenteConquistado("AmericaSur");

        assertFalse(resultado);
    }
}
