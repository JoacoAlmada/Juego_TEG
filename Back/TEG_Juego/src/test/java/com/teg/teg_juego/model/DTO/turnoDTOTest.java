package com.teg.teg_juego.model.DTO;

import com.teg.teg_juego.model.enums.Fase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class turnoDTOTest {

    @Test
    public void testConstructorSinParametros() {
        // Act
        turnoDTO dto = new turnoDTO();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getRonda());
        assertNull(dto.getTurno());
        assertNull(dto.getFase());
    }

    @Test
    public void testSettersYGetters() {
        // Arrange
        turnoDTO turno = new turnoDTO();
        Integer rondaEsperada = 5;
        Integer turnoEsperado = 2;
        Fase faseEsperada = Fase.ATAQUE; // Usar valor del enum

        // Act
        turno.setRonda(rondaEsperada);
        turno.setTurno(turnoEsperado);
        turno.setFase(faseEsperada);

        // Assert
        assertEquals(rondaEsperada, turno.getRonda());
        assertEquals(turnoEsperado, turno.getTurno());
        assertEquals(faseEsperada, turno.getFase());
    }

    @Test
    public void testSetRonda() {
        // Arrange
        turnoDTO turno = new turnoDTO();
        Integer rondaEsperada = 10;

        // Act
        turno.setRonda(rondaEsperada);

        // Assert
        assertEquals(rondaEsperada, turno.getRonda());
    }

    @Test
    public void testSetTurno() {
        // Arrange
        turnoDTO turno = new turnoDTO();
        Integer turnoEsperado = 3;

        // Act
        turno.setTurno(turnoEsperado);

        // Assert
        assertEquals(turnoEsperado, turno.getTurno());
    }

    @Test
    public void testSetFase() {
        // Arrange
        turnoDTO turno = new turnoDTO();
        Fase faseEsperada = Fase.COLOCACION; // Usar valor del enum

        // Act
        turno.setFase(faseEsperada);

        // Assert
        assertEquals(faseEsperada, turno.getFase());
    }

    @Test
    public void testSetRondaConNull() {
        // Arrange
        turnoDTO turno = new turnoDTO();

        // Act
        turno.setRonda(null);

        // Assert
        assertNull(turno.getRonda());
    }

    @Test
    public void testSetTurnoConNull() {
        // Arrange
        turnoDTO turno = new turnoDTO();

        // Act
        turno.setTurno(null);

        // Assert
        assertNull(turno.getTurno());
    }

    @Test
    public void testSetFaseConNull() {
        // Arrange
        turnoDTO turno = new turnoDTO();

        // Act
        turno.setFase(null);

        // Assert
        assertNull(turno.getFase());
    }

    @Test
    public void testValoresLimite() {
        // Arrange
        turnoDTO turno = new turnoDTO();
        Integer rondaMinima = 0;
        Integer rondaMaxima = Integer.MAX_VALUE;
        Integer turnoMinimo = 0;
        Integer turnoMaximo = Integer.MAX_VALUE;

        // Act
        turno.setRonda(rondaMinima);
        turno.setTurno(turnoMinimo);

        // Assert
        assertEquals(rondaMinima, turno.getRonda());
        assertEquals(turnoMinimo, turno.getTurno());

        // Act
        turno.setRonda(rondaMaxima);
        turno.setTurno(turnoMaximo);

        // Assert
        assertEquals(rondaMaxima, turno.getRonda());
        assertEquals(turnoMaximo, turno.getTurno());
    }

    @Test
    public void testEstadoInicialDespuesDeCrearObjeto() {
        // Act
        turnoDTO nuevoTurno = new turnoDTO();

        // Assert - Verificar que todos los campos están en null inicialmente
        assertNull(nuevoTurno.getRonda(), "La ronda debería ser null inicialmente");
        assertNull(nuevoTurno.getTurno(), "El turno debería ser null inicialmente");
        assertNull(nuevoTurno.getFase(), "La fase debería ser null inicialmente");
    }

    @Test
    public void testModificacionesMultiples() {
        // Arrange
        turnoDTO turno = new turnoDTO();
        Integer primeraRonda = 1;
        Integer segundaRonda = 5;
        Integer primerTurno = 2;
        Integer segundoTurno = 8;

        // Act & Assert - Primera modificación
        turno.setRonda(primeraRonda);
        turno.setTurno(primerTurno);
        assertEquals(primeraRonda, turno.getRonda());
        assertEquals(primerTurno, turno.getTurno());

        // Act & Assert - Segunda modificación
        turno.setRonda(segundaRonda);
        turno.setTurno(segundoTurno);
        assertEquals(segundaRonda, turno.getRonda());
        assertEquals(segundoTurno, turno.getTurno());
    }
}