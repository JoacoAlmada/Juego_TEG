package com.teg.teg_juego.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultadoAtaqueTest {

    private ResultadoAtaque resultadoAtaque;
    private List<Integer> dadosAtaque;
    private List<Integer> dadosDefensa;

    @BeforeEach
    void setUp() {
        dadosAtaque = Arrays.asList(6, 5, 3);
        dadosDefensa = Arrays.asList(4, 2);
        resultadoAtaque = new ResultadoAtaque();
    }

    @Test
    void testConstructorVacio() {
        ResultadoAtaque resultado = new ResultadoAtaque();

        assertNotNull(resultado);
        assertNull(resultado.getResultado());
        assertNull(resultado.getDadosAtaque());
        assertNull(resultado.getDadosDefensa());
    }

    @Test
    void testConstructorCompleto() {
        ResultadoAtaque resultado = new ResultadoAtaque("conquista", dadosAtaque, dadosDefensa);

        assertNotNull(resultado);
        assertEquals("conquista", resultado.getResultado());
        assertEquals(dadosAtaque, resultado.getDadosAtaque());
        assertEquals(dadosDefensa, resultado.getDadosDefensa());
    }

    @Test
    void testGetterYSetterResultado() {
        // Test getter inicial (null)
        assertNull(resultadoAtaque.getResultado());

        // Test setter y getter
        resultadoAtaque.setResultado("ataque");
        assertEquals("ataque", resultadoAtaque.getResultado());

        // Test con "conquista"
        resultadoAtaque.setResultado("conquista");
        assertEquals("conquista", resultadoAtaque.getResultado());

        // Test con null
        resultadoAtaque.setResultado(null);
        assertNull(resultadoAtaque.getResultado());

        // Test con string vacío
        resultadoAtaque.setResultado("");
        assertEquals("", resultadoAtaque.getResultado());
    }

    @Test
    void testGetterYSetterDadosAtaque() {
        // Test getter inicial (null)
        assertNull(resultadoAtaque.getDadosAtaque());

        // Test setter y getter
        resultadoAtaque.setDadosAtaque(dadosAtaque);
        assertEquals(dadosAtaque, resultadoAtaque.getDadosAtaque());
        assertEquals(3, resultadoAtaque.getDadosAtaque().size());
        assertEquals(Integer.valueOf(6), resultadoAtaque.getDadosAtaque().get(0));
        assertEquals(Integer.valueOf(5), resultadoAtaque.getDadosAtaque().get(1));
        assertEquals(Integer.valueOf(3), resultadoAtaque.getDadosAtaque().get(2));

        // Test con lista vacía
        List<Integer> listaVacia = Arrays.asList();
        resultadoAtaque.setDadosAtaque(listaVacia);
        assertEquals(listaVacia, resultadoAtaque.getDadosAtaque());
        assertTrue(resultadoAtaque.getDadosAtaque().isEmpty());

        // Test con null
        resultadoAtaque.setDadosAtaque(null);
        assertNull(resultadoAtaque.getDadosAtaque());

        // Test con diferentes valores
        List<Integer> otrosDados = Arrays.asList(1, 1, 1);
        resultadoAtaque.setDadosAtaque(otrosDados);
        assertEquals(otrosDados, resultadoAtaque.getDadosAtaque());
    }

    @Test
    void testGetterYSetterDadosDefensa() {
        // Test getter inicial (null)
        assertNull(resultadoAtaque.getDadosDefensa());

        // Test setter y getter
        resultadoAtaque.setDadosDefensa(dadosDefensa);
        assertEquals(dadosDefensa, resultadoAtaque.getDadosDefensa());
        assertEquals(2, resultadoAtaque.getDadosDefensa().size());
        assertEquals(Integer.valueOf(4), resultadoAtaque.getDadosDefensa().get(0));
        assertEquals(Integer.valueOf(2), resultadoAtaque.getDadosDefensa().get(1));

        // Test con lista vacía
        List<Integer> listaVacia = Arrays.asList();
        resultadoAtaque.setDadosDefensa(listaVacia);
        assertEquals(listaVacia, resultadoAtaque.getDadosDefensa());
        assertTrue(resultadoAtaque.getDadosDefensa().isEmpty());

        // Test con null
        resultadoAtaque.setDadosDefensa(null);
        assertNull(resultadoAtaque.getDadosDefensa());

        // Test con un solo dado
        List<Integer> unDado = Arrays.asList(6);
        resultadoAtaque.setDadosDefensa(unDado);
        assertEquals(unDado, resultadoAtaque.getDadosDefensa());
        assertEquals(1, resultadoAtaque.getDadosDefensa().size());
    }

    @Test
    void testEquals() {
        ResultadoAtaque resultado1 = new ResultadoAtaque("ataque", dadosAtaque, dadosDefensa);
        ResultadoAtaque resultado2 = new ResultadoAtaque("ataque", dadosAtaque, dadosDefensa);
        ResultadoAtaque resultado3 = new ResultadoAtaque("conquista", dadosAtaque, dadosDefensa);
        ResultadoAtaque resultado4 = new ResultadoAtaque("ataque", Arrays.asList(1, 2), dadosDefensa);
        ResultadoAtaque resultado5 = new ResultadoAtaque("ataque", dadosAtaque, Arrays.asList(1));

        // Reflexividad
        assertEquals(resultado1, resultado1);

        // Simetría
        assertEquals(resultado1, resultado2);
        assertEquals(resultado2, resultado1);

        // Objetos iguales
        assertEquals(resultado1, resultado2);

        // Diferentes resultados
        assertNotEquals(resultado1, resultado3);

        // Diferentes dados de ataque
        assertNotEquals(resultado1, resultado4);

        // Diferentes dados de defensa
        assertNotEquals(resultado1, resultado5);

        // Comparación con null
        assertNotEquals(resultado1, null);

        // Comparación con objeto de diferente clase
        assertNotEquals(resultado1, "string");

        // Test con valores null
        ResultadoAtaque resultadoNull1 = new ResultadoAtaque(null, null, null);
        ResultadoAtaque resultadoNull2 = new ResultadoAtaque(null, null, null);
        assertEquals(resultadoNull1, resultadoNull2);
    }

    @Test
    void testHashCode() {
        ResultadoAtaque resultado1 = new ResultadoAtaque("ataque", dadosAtaque, dadosDefensa);
        ResultadoAtaque resultado2 = new ResultadoAtaque("ataque", dadosAtaque, dadosDefensa);
        ResultadoAtaque resultado3 = new ResultadoAtaque("conquista", dadosAtaque, dadosDefensa);

        // Objetos iguales deben tener el mismo hashCode
        assertEquals(resultado1.hashCode(), resultado2.hashCode());

        // Objetos diferentes pueden tener hashCode diferente
        assertNotEquals(resultado1.hashCode(), resultado3.hashCode());

        // Test consistencia - múltiples llamadas al mismo objeto
        int hashCode1 = resultado1.hashCode();
        int hashCode2 = resultado1.hashCode();
        assertEquals(hashCode1, hashCode2);

        // Test con valores null
        ResultadoAtaque resultadoNull = new ResultadoAtaque(null, null, null);
        int hashCodeNull = resultadoNull.hashCode();
        assertTrue(hashCodeNull >= 0 || hashCodeNull < 0); // Solo verificamos que no lance excepción
    }

    @Test
    void testToString() {
        ResultadoAtaque resultado = new ResultadoAtaque("conquista", dadosAtaque, dadosDefensa);
        String toString = resultado.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("ResultadoAtaque"));
        assertTrue(toString.contains("conquista"));
        assertTrue(toString.contains(dadosAtaque.toString()));
        assertTrue(toString.contains(dadosDefensa.toString()));

        // Test con valores null
        ResultadoAtaque resultadoNull = new ResultadoAtaque(null, null, null);
        String toStringNull = resultadoNull.toString();
        assertNotNull(toStringNull);
        assertTrue(toStringNull.contains("ResultadoAtaque"));
        assertTrue(toStringNull.contains("null"));
    }

    @Test
    void testCambiosEnObjeto() {
        // Test que el objeto es mutable
        resultadoAtaque.setResultado("ataque");
        resultadoAtaque.setDadosAtaque(dadosAtaque);
        resultadoAtaque.setDadosDefensa(dadosDefensa);

        assertEquals("ataque", resultadoAtaque.getResultado());
        assertEquals(dadosAtaque, resultadoAtaque.getDadosAtaque());
        assertEquals(dadosDefensa, resultadoAtaque.getDadosDefensa());

        // Cambiar valores
        List<Integer> nuevosAtaque = Arrays.asList(2, 1);
        List<Integer> nuevosDefensa = Arrays.asList(5);

        resultadoAtaque.setResultado("conquista");
        resultadoAtaque.setDadosAtaque(nuevosAtaque);
        resultadoAtaque.setDadosDefensa(nuevosDefensa);

        assertEquals("conquista", resultadoAtaque.getResultado());
        assertEquals(nuevosAtaque, resultadoAtaque.getDadosAtaque());
        assertEquals(nuevosDefensa, resultadoAtaque.getDadosDefensa());
    }

    @Test
    void testEscenarioAtaque() {
        // Simular un escenario de ataque típico
        List<Integer> dadosAtaqueReal = Arrays.asList(5, 4, 2);
        List<Integer> dadosDefensaReal = Arrays.asList(6, 3);

        ResultadoAtaque ataque = new ResultadoAtaque("ataque", dadosAtaqueReal, dadosDefensaReal);

        assertEquals("ataque", ataque.getResultado());
        assertEquals(3, ataque.getDadosAtaque().size());
        assertEquals(2, ataque.getDadosDefensa().size());
        assertEquals(Integer.valueOf(5), ataque.getDadosAtaque().get(0));
        assertEquals(Integer.valueOf(6), ataque.getDadosDefensa().get(0));
    }

    @Test
    void testEscenarioConquista() {
        // Simular un escenario de conquista típico
        List<Integer> dadosAtaqueReal = Arrays.asList(6, 6, 5);
        List<Integer> dadosDefensaReal = Arrays.asList(3, 1);

        ResultadoAtaque conquista = new ResultadoAtaque("conquista", dadosAtaqueReal, dadosDefensaReal);

        assertEquals("conquista", conquista.getResultado());
        assertEquals(3, conquista.getDadosAtaque().size());
        assertEquals(2, conquista.getDadosDefensa().size());
        assertTrue(conquista.getDadosAtaque().get(0) >= conquista.getDadosDefensa().get(0));
    }

    @Test
    void testDiferentesCantidadesDados() {
        // Test con 1 dado de ataque
        ResultadoAtaque unDado = new ResultadoAtaque("ataque", Arrays.asList(4), Arrays.asList(5));
        assertEquals(1, unDado.getDadosAtaque().size());
        assertEquals(1, unDado.getDadosDefensa().size());

        // Test con 2 dados de ataque
        ResultadoAtaque dosDados = new ResultadoAtaque("ataque", Arrays.asList(6, 3), Arrays.asList(4, 2));
        assertEquals(2, dosDados.getDadosAtaque().size());
        assertEquals(2, dosDados.getDadosDefensa().size());

        // Test con máximo dados (3 ataque, 2 defensa)
        ResultadoAtaque maxDados = new ResultadoAtaque("conquista", Arrays.asList(6, 5, 4), Arrays.asList(5, 3));
        assertEquals(3, maxDados.getDadosAtaque().size());
        assertEquals(2, maxDados.getDadosDefensa().size());
    }

    @Test
    void testValoresLimiteDados() {
        // Test con valores mínimos de dados (1)
        List<Integer> dadosMinimos = Arrays.asList(1, 1, 1);
        ResultadoAtaque minimo = new ResultadoAtaque("ataque", dadosMinimos, Arrays.asList(1));

        assertTrue(minimo.getDadosAtaque().stream().allMatch(d -> d == 1));
        assertEquals(Integer.valueOf(1), minimo.getDadosDefensa().get(0));

        // Test con valores máximos de dados (6)
        List<Integer> dadosMaximos = Arrays.asList(6, 6, 6);
        ResultadoAtaque maximo = new ResultadoAtaque("conquista", dadosMaximos, Arrays.asList(6, 6));

        assertTrue(maximo.getDadosAtaque().stream().allMatch(d -> d == 6));
        assertTrue(maximo.getDadosDefensa().stream().allMatch(d -> d == 6));
    }

    @Test
    void testInmutabilidadDeListas() {
        // Verificar que las listas se pueden modificar después de asignar
        List<Integer> dadosOriginales = Arrays.asList(3, 2, 1);
        resultadoAtaque.setDadosAtaque(dadosOriginales);

        assertEquals(dadosOriginales, resultadoAtaque.getDadosAtaque());

        // Cambiar la referencia no afecta el objeto original si se usa una nueva lista
        List<Integer> nuevaLista = Arrays.asList(6, 5, 4);
        resultadoAtaque.setDadosAtaque(nuevaLista);

        assertNotEquals(dadosOriginales, resultadoAtaque.getDadosAtaque());
        assertEquals(nuevaLista, resultadoAtaque.getDadosAtaque());
    }

}