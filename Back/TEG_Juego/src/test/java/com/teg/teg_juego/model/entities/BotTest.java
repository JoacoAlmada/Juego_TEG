package com.teg.teg_juego.model.entities;

import com.teg.teg_juego.model.DTO.CanjearDTO;
import com.teg.teg_juego.model.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BotTest {


    private Bot bot;

    @Mock
    private Partida mockPartida;

    @Mock
    private Pais mockPais1;

    @Mock
    private Pais mockPais2;

    @Mock
    private Pais mockPaisEnemigo;

    @Mock
    private Continente mockContinente;

    @Mock
    private Objetivo mockObjetivo;

    @Mock
    private TarjetaPais mockTarjeta1;

    @Mock
    private TarjetaPais mockTarjeta2;

    @Mock
    private TarjetaPais mockTarjeta3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bot = new Bot("Bot_Test", Color.ROJO, DificultadBot.NOVATO);
    }

    @Test
    void testConstructorVacio() {
        Bot botVacio = new Bot();
        assertNotNull(botVacio);
    }

    @Test
    void testConstructorCompleto() {
        Bot botCompleto = new Bot("TestBot", Color.AZUL, DificultadBot.EXPERTO);
        assertEquals("TestBot", botCompleto.getNombreJ());
        assertEquals(Color.AZUL, botCompleto.getColor());
        assertEquals(DificultadBot.EXPERTO, botCompleto.getDificultad());
        assertEquals(TipoJugador.BOT, botCompleto.getTipoJ());
    }

    @Test
    void testNombre() {
        String expectedNombre = "Bot_Test";
        assertEquals(expectedNombre, bot.getNombreJ());
    }

    @Test
    void testColor() {
        assertEquals(Color.ROJO, bot.getColor());
    }

    @Test
    void testTipoJugador() {
        assertEquals(TipoJugador.BOT, bot.getTipoJ());
    }

    @Test
    void testDificultad() {
        assertEquals(DificultadBot.NOVATO, bot.getDificultad());

        bot.setDificultad(DificultadBot.EXPERTO);
        assertEquals(DificultadBot.EXPERTO, bot.getDificultad());
    }

    @Test
    void testEqualsYHashCode() {
        Bot bot1 = new Bot("Bot_Test", Color.ROJO, DificultadBot.NOVATO);
        Bot bot2 = new Bot("Bot_Test", Color.ROJO, DificultadBot.NOVATO);
        Bot bot3 = new Bot("Bot_Otro", Color.AZUL, DificultadBot.EXPERTO);

        // Forzar mismo ID para probar igualdad lógica si es necesario
        bot1.setId(1);
        bot2.setId(1);

        assertEquals(bot1, bot2);
        assertEquals(bot1.hashCode(), bot2.hashCode());

        assertNotEquals(bot1, bot3);
        assertNotEquals(bot1, null);
        assertNotEquals(bot1, "otro tipo");
    }

    @Test
    void testTomarDecision_FaseColocacion_Ronda1() {
        // Setup
        bot.setPartida(mockPartida);
        when(mockPartida.getFase()).thenReturn(Fase.COLOCACION);
        when(mockPartida.getRonda()).thenReturn(1);

        // Mock para que tenga fichas y países
        Bot spyBot = spy(bot);
        doReturn(5).when(spyBot).getFichasJ();
        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();
        doNothing().when(spyBot).colocar(any(Pais.class), anyInt());
        doReturn(new ArrayList<>()).when(spyBot).getTarjetas();

        // Execute
        spyBot.tomarDecision();

        // Verify
        verify(mockPartida).pasarFase();
    }

    @Test
    void testTomarDecision_FaseColocacion_Ronda3() {
        // Setup
        bot.setPartida(mockPartida);
        when(mockPartida.getFase()).thenReturn(Fase.COLOCACION);
        when(mockPartida.getRonda()).thenReturn(3);

        // Execute
        bot.tomarDecision();

        // Verify - En ronda 3 no se colocan tropas
        verify(mockPartida).pasarFase();
    }

    @Test
    void testTomarDecision_FaseAtaque_Ronda3() {
        // Setup
        bot.setPartida(mockPartida);
        when(mockPartida.getFase()).thenReturn(Fase.ATAQUE);
        when(mockPartida.getRonda()).thenReturn(3);

        Bot spyBot = spy(bot);
        doReturn(Arrays.asList()).when(spyBot).getPaises();

        // Execute
        spyBot.tomarDecision();

        // Verify
        verify(mockPartida).pasarFase();
    }

    @Test
    void testTomarDecision_FaseAtaque_Ronda2() {
        // Setup
        bot.setPartida(mockPartida);
        when(mockPartida.getFase()).thenReturn(Fase.ATAQUE);
        when(mockPartida.getRonda()).thenReturn(2);

        // Execute
        bot.tomarDecision();

        // Verify - En ronda 2 no se ataca
        verify(mockPartida).pasarFase();
    }

    @Test
    void testTomarDecision_FaseReagrupacion_Ronda3() {
        // Setup
        bot.setPartida(mockPartida);
        when(mockPartida.getFase()).thenReturn(Fase.REAGRUPACION);
        when(mockPartida.getRonda()).thenReturn(3);

        Bot spyBot = spy(bot);
        doReturn(Arrays.asList()).when(spyBot).getPaises();

        // Execute
        spyBot.tomarDecision();

        // Verify
        verify(mockPartida).pasarFase();
    }

    @Test
    void testTomarDecision_FaseReagrupacion_Ronda2() {
        // Setup
        bot.setPartida(mockPartida);
        when(mockPartida.getFase()).thenReturn(Fase.REAGRUPACION);
        when(mockPartida.getRonda()).thenReturn(2);

        // Execute
        bot.tomarDecision();

        // Verify - En ronda 2 no se reagrupa
        verify(mockPartida).pasarFase();
    }

    @Test
    void testTomarDecision_FaseDesconocida() {
        // Setup
        bot.setPartida(mockPartida);
        when(mockPartida.getFase()).thenReturn(null);
        when(mockPartida.getRonda()).thenReturn(1);

        // Execute
        bot.tomarDecision();

        // Verify
        verify(mockPartida).pasarFase();
    }

    @Test
    void testColocarSegunDificultad_Novato() {
        // Setup
        bot.setDificultad(DificultadBot.NOVATO);
        Bot spyBot = spy(bot);

        doReturn(3).when(spyBot).getFichasJ();
        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();
        doNothing().when(spyBot).colocar(any(Pais.class), anyInt());
        doReturn(new ArrayList<>()).when(spyBot).getTarjetas();

        // Execute - usando reflexión para acceder al método privado
        try {
            var method = Bot.class.getDeclaredMethod("colocarSegunDificultad");
            method.setAccessible(true);
            method.invoke(spyBot);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }

        // Verify
        verify(spyBot, atLeastOnce()).getFichasJ();
    }


    @Test
    void testColocarSegunDificultad_ConCanjeTarjetas() {
        // Setup
        bot.setDificultad(DificultadBot.NOVATO);
        bot.setNumeroCanje(1);  // <-- Inicialización necesaria

        Bot spyBot = spy(bot);

        // Mock tarjetas para que tenga más de 5 (NOVATO)
        List<TarjetaPais> tarjetas = Arrays.asList(mockTarjeta1, mockTarjeta2, mockTarjeta3,
                mock(TarjetaPais.class), mock(TarjetaPais.class), mock(TarjetaPais.class));

        doReturn(3).when(spyBot).getFichasJ();
        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();
        doNothing().when(spyBot).colocar(any(Pais.class), anyInt());
        doReturn(tarjetas).when(spyBot).getTarjetas();

        // Mock para canje de tarjetas
        when(mockTarjeta1.getSimbolo()).thenReturn(Simbolo.INFANTERIA);
        when(mockTarjeta2.getSimbolo()).thenReturn(Simbolo.CABALLERIA);
        when(mockTarjeta3.getSimbolo()).thenReturn(Simbolo.ARTILLERIA);

        CanjearDTO mockCanje = mock(CanjearDTO.class);
        when(mockCanje.isExito()).thenReturn(true);

        try {
            var method = Bot.class.getDeclaredMethod("colocarSegunDificultad");
            method.setAccessible(true);
            method.invoke(spyBot);
        } catch (Exception e) {
            e.printStackTrace();  // Muestra la traza del error real
            fail("No se pudo ejecutar el método privado: " + e.getCause());
        }
    }

    @Test
    void testAtacarSegunDificultad_Novato() {
        // Setup
        bot.setDificultad(DificultadBot.NOVATO);
        Bot spyBot = spy(bot);

        // Mock países para ataque
        when(mockPais1.getEjercito()).thenReturn(3);
        when(mockPais1.getVecinos()).thenReturn(Arrays.asList(mockPaisEnemigo));
        when(mockPaisEnemigo.getJugador()).thenReturn(mock(Jugador.class));
        when(mockPaisEnemigo.getEjercito()).thenReturn(1);

        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();

        ResultadoAtaque mockResultado = mock(ResultadoAtaque.class);
        when(mockResultado.getResultado()).thenReturn("conquista");
        doReturn(mockResultado).when(spyBot).atacar(any(Pais.class), any(Pais.class));

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("atacarSegunDificultad");
            method.setAccessible(true);
            boolean resultado = (boolean) method.invoke(spyBot);
            assertTrue(resultado);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }
    }

    @Test
    void testAtacarSegunDificultad_Intermedio() {
        // Setup
        bot.setDificultad(DificultadBot.INTERMEDIO);
        Bot spyBot = spy(bot);

        // Mock países para ataque
        when(mockPais1.getEjercito()).thenReturn(4);
        when(mockPais1.getVecinos()).thenReturn(Arrays.asList(mockPaisEnemigo));
        when(mockPaisEnemigo.getJugador()).thenReturn(mock(Jugador.class));
        when(mockPaisEnemigo.getEjercito()).thenReturn(2);

        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();

        ResultadoAtaque mockResultado = mock(ResultadoAtaque.class);
        when(mockResultado.getResultado()).thenReturn("ataque");
        doReturn(mockResultado).when(spyBot).atacar(any(Pais.class), any(Pais.class));

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("atacarSegunDificultad");
            method.setAccessible(true);
            boolean resultado = (boolean) method.invoke(spyBot);
            assertTrue(resultado);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }
    }

    @Test
    void testAtacarSegunDificultad_Experto() {
        // Setup
        bot.setDificultad(DificultadBot.EXPERTO);
        Bot spyBot = spy(bot);

        // Mock objetivo
        when(mockObjetivo.getDescripcion()).thenReturn("Conquistar America del Sur");
        when(mockContinente.getNombre()).thenReturn("America del Sur");
        doReturn(mockObjetivo).when(spyBot).getObjetivo();

        // Mock países para ataque
        when(mockPais1.getEjercito()).thenReturn(5);
        when(mockPais1.getVecinos()).thenReturn(Arrays.asList(mockPaisEnemigo));
        when(mockPaisEnemigo.getJugador()).thenReturn(mock(Jugador.class));
        when(mockPaisEnemigo.getEjercito()).thenReturn(2);
        when(mockPaisEnemigo.getContinente()).thenReturn(mockContinente);

        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();

        ResultadoAtaque mockResultado = mock(ResultadoAtaque.class);
        when(mockResultado.getResultado()).thenReturn("conquista");
        doReturn(mockResultado).when(spyBot).atacar(any(Pais.class), any(Pais.class));

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("atacarSegunDificultad");
            method.setAccessible(true);
            boolean resultado = (boolean) method.invoke(spyBot);
            assertTrue(resultado);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }
    }

    @Test
    void testReagruparSegunDificultad_Novato() {
        // Setup
        bot.setDificultad(DificultadBot.NOVATO);
        Bot spyBot = spy(bot);

        // Mock países para reagrupación
        when(mockPais1.getEjercito()).thenReturn(5);
        when(mockPais1.getVecinos()).thenReturn(Arrays.asList(mockPais2));
        when(mockPais2.getJugador()).thenReturn(spyBot);

        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();
        doNothing().when(spyBot).mover(any(Pais.class), any(Pais.class), anyInt());

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("reagruparSegunDificultad");
            method.setAccessible(true);
            method.invoke(spyBot);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }

        // Verify
        verify(spyBot).mover(mockPais1, mockPais2, 4);
    }

    @Test
    void testAtacarComoNovato_SinAtaquesPosibles() {
        // Setup
        Bot spyBot = spy(bot);

        // Mock países sin ataques posibles
        when(mockPais1.getEjercito()).thenReturn(1); // No tiene suficientes tropas
        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("atacarComoNovato");
            method.setAccessible(true);
            boolean resultado = (boolean) method.invoke(spyBot);
            assertFalse(resultado);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }
    }

    @Test
    void testAtacarComoNovato_ConErrorEnAtaque() {
        // Setup
        Bot spyBot = spy(bot);

        // Mock países para ataque
        when(mockPais1.getEjercito()).thenReturn(3);
        when(mockPais1.getVecinos()).thenReturn(Arrays.asList(mockPaisEnemigo));
        when(mockPaisEnemigo.getJugador()).thenReturn(mock(Jugador.class));
        when(mockPaisEnemigo.getEjercito()).thenReturn(1);

        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();
        doThrow(new RuntimeException("Error de ataque")).when(spyBot).atacar(any(Pais.class), any(Pais.class));

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("atacarComoNovato");
            method.setAccessible(true);
            boolean resultado = (boolean) method.invoke(spyBot);
            assertFalse(resultado);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }
    }

    @Test
    void testIntentarCanjearTarjetas_SinSuficientesTarjetas() {
        // Setup
        Bot spyBot = spy(bot);
        doReturn(Arrays.asList(mockTarjeta1, mockTarjeta2)).when(spyBot).getTarjetas(); // Solo 2 tarjetas

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("intentarCanjearTarjetas");
            method.setAccessible(true);
            CanjearDTO resultado = (CanjearDTO) method.invoke(spyBot);
            assertNull(resultado);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }
    }

    @Test
    void testGenerarCombinacionesDeTres() {
        // Setup
        Bot spyBot = spy(bot);
        TarjetaPais tarjeta3 = mock(TarjetaPais.class);
        TarjetaPais tarjeta4 = mock(TarjetaPais.class);

        List<TarjetaPais> tarjetas = Arrays.asList(mockTarjeta1, mockTarjeta2, tarjeta3, tarjeta4);

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("generarCombinacionesDeTres", List.class);
            method.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<List<TarjetaPais>> resultado = (List<List<TarjetaPais>>) method.invoke(spyBot, tarjetas);

            // Verify - Con 4 tarjetas debe generar 4 combinaciones de 3
            assertEquals(4, resultado.size());
            resultado.forEach(combinacion -> assertEquals(3, combinacion.size()));
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }
    }

    @Test
    void testColocarFichasBasico_SinPaises() {
        // Setup
        Bot spyBot = spy(bot);
        doReturn(Arrays.asList()).when(spyBot).getPaises(); // Sin países
        doReturn(5).when(spyBot).getFichasJ();

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("colocarFichasBasico");
            method.setAccessible(true);
            method.invoke(spyBot);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }

        // Verify - No debe intentar colocar fichas
        verify(spyBot, never()).colocar(any(Pais.class), anyInt());
    }

    @Test
    void testColocarFichasBasico_SinFichas() {
        // Setup
        Bot spyBot = spy(bot);
        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();
        doReturn(0).when(spyBot).getFichasJ(); // Sin fichas

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("colocarFichasBasico");
            method.setAccessible(true);
            method.invoke(spyBot);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }

        // Verify - No debe intentar colocar fichas
        verify(spyBot, never()).colocar(any(Pais.class), anyInt());
    }

    @Test
    void testReubicarTropasSimple_SinMovimientos() {
        // Setup
        Bot spyBot = spy(bot);

        // Mock país sin suficientes tropas
        when(mockPais1.getEjercito()).thenReturn(2); // Necesita más de 3
        doReturn(Arrays.asList(mockPais1)).when(spyBot).getPaises();

        // Execute
        try {
            var method = Bot.class.getDeclaredMethod("reubicarTropasSimple");
            method.setAccessible(true);
            method.invoke(spyBot);
        } catch (Exception e) {
            fail("No se pudo ejecutar el método privado");
        }

        // Verify - No debe mover tropas
        verify(spyBot, never()).mover(any(Pais.class), any(Pais.class), anyInt());
    }
    @Test
    void testColocarFichasEstrategico() throws Exception {
        // Crear bot spy
        Bot botSpy = spy(new Bot());

        // Crear países mock
        Pais pais1 = mock(Pais.class);
        Pais pais2 = mock(Pais.class);
        Pais pais3 = mock(Pais.class);

        // Simular nombres
        when(pais1.getNombre()).thenReturn("Pais1");
        when(pais2.getNombre()).thenReturn("Pais2");
        when(pais3.getNombre()).thenReturn("Pais3");

        // Simular ejercito (para ordenar)
        when(pais1.getEjercito()).thenReturn(5);
        when(pais2.getEjercito()).thenReturn(2);
        when(pais3.getEjercito()).thenReturn(8);

        // Crear vecinos y jugadores para simular amenaza
        Pais vecinoEnemigo = mock(Pais.class);
        Jugador enemigo = mock(Jugador.class);
        Jugador botJugador = botSpy;

        // Vecino enemigo asignado
        when(vecinoEnemigo.getJugador()).thenReturn(enemigo);

        // Pais1 tiene vecino enemigo, Pais2 no, Pais3 sí
        when(pais1.getVecinos()).thenReturn(List.of(vecinoEnemigo));
        when(pais2.getVecinos()).thenReturn(List.of(pais1)); // mismo jugador, no amenaza
        when(pais3.getVecinos()).thenReturn(List.of(vecinoEnemigo));

        // Los países pertenecen al bot
        when(pais1.getJugador()).thenReturn(botJugador);
        when(pais2.getJugador()).thenReturn(botJugador);
        when(pais3.getJugador()).thenReturn(botJugador);

        // Mock getPaises()
        doReturn(List.of(pais1, pais2, pais3)).when(botSpy).getPaises();

        // Configurar fichas iniciales
        // Supongamos 4 fichas
        doReturn(4).when(botSpy).getFichasJ();

        // Cuando colocar sea llamado, se "consume" 1 ficha, así que debemos simularlo
        // Para eso, usamos un contador en un array para modificar el valor simulado de getFichasJ
        final int[] fichasRestantes = {4};

        doAnswer(invocation -> {
            Pais p = invocation.getArgument(0);
            int cant = invocation.getArgument(1);
            fichasRestantes[0] -= cant;
            // Actualizamos el mock getFichasJ para devolver el nuevo valor
            doReturn(fichasRestantes[0]).when(botSpy).getFichasJ();
            return null;
        }).when(botSpy).colocar(any(Pais.class), anyInt());

        // Ejecutar método privado con reflexión
        var method = Bot.class.getDeclaredMethod("colocarFichasEstrategico");
        method.setAccessible(true);
        method.invoke(botSpy);

        // Verificar que colocar se llamó 4 veces (todas las fichas usadas)
        verify(botSpy, times(4)).colocar(any(Pais.class), eq(1));

        // Capturar argumentos para verificar orden
        ArgumentCaptor<Pais> paisCaptor = ArgumentCaptor.forClass(Pais.class);
        verify(botSpy, times(4)).colocar(paisCaptor.capture(), eq(1));
        List<Pais> paisesColocados = paisCaptor.getAllValues();

        assertTrue(paisesColocados.get(0) == pais1 || paisesColocados.get(0) == pais3);
        assertTrue(paisesColocados.get(1) == pais1 || paisesColocados.get(1) == pais3);

        // Las dos últimas fichas pueden ir a cualquiera (incluye pais2)
        assertEquals(4, paisesColocados.size());
    }
}