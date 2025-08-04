package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.partidaRepository;
import com.teg.teg_juego.model.DTO.turnoDTO;
import com.teg.teg_juego.model.entities.Partida;
import com.teg.teg_juego.model.enums.Fase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurnoServiceTest {

    @Mock
    private partidaRepository partidaRepository;

    @InjectMocks
    private TurnoService turnoService;

    private Partida partida;
    private Integer partidaId;

    @BeforeEach
    void setUp() {
        partidaId = 1;
        partida = new Partida();
        partida.setId(partidaId);
        partida.setRonda(2);
        partida.setTurno(3);
        partida.setFase(Fase.COLOCACION);
    }

    @Test
    void testGetTurnoActual_PartidaExiste_DeberiaRetornarTurno() {
        // Arrange
        when(partidaRepository.findById(partidaId)).thenReturn(Optional.of(partida));

        // Act
        Integer resultado = turnoService.getTurnoActual(partidaId);

        // Assert
        assertNotNull(resultado);
        assertEquals(3, resultado);
        verify(partidaRepository, times(1)).findById(partidaId);
    }

    @Test
    void testGetTurnoActual_PartidaNoExiste_DeberiaLanzarRuntimeException() {
        // Arrange
        when(partidaRepository.findById(partidaId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> turnoService.getTurnoActual(partidaId));

        assertEquals("Partida no encontrada", exception.getMessage());
        verify(partidaRepository, times(1)).findById(partidaId);
    }

    @Test
    void testGetTurnoActual_ConTurnoCero_DeberiaRetornarCero() {
        // Arrange
        partida.setTurno(0);
        when(partidaRepository.findById(partidaId)).thenReturn(Optional.of(partida));

        // Act
        Integer resultado = turnoService.getTurnoActual(partidaId);

        // Assert
        assertEquals(0, resultado);
        verify(partidaRepository, times(1)).findById(partidaId);
    }

    @Test
    void testGetHistorialTurnos_PartidaExiste_DeberiaRetornarTurnoDTO() {
        // Arrange
        when(partidaRepository.findById(partidaId)).thenReturn(Optional.of(partida));

        // Act
        turnoDTO resultado = turnoService.getHistorialTurnos(partidaId);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getRonda());
        assertEquals(3, resultado.getTurno());
        assertEquals(Fase.COLOCACION, resultado.getFase());
        verify(partidaRepository, times(1)).findById(partidaId);
    }

    @Test
    void testGetHistorialTurnos_PartidaNoExiste_DeberiaLanzarRuntimeException() {
        // Arrange
        when(partidaRepository.findById(partidaId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> turnoService.getHistorialTurnos(partidaId));

        assertEquals("Partida no encontrada", exception.getMessage());
        verify(partidaRepository, times(1)).findById(partidaId);
    }

    @Test
    void testGetHistorialTurnos_ConValoresMinimos_DeberiaRetornarTurnoDTOCorrectamente() {
        // Arrange
        partida.setRonda(1);
        partida.setTurno(1);
        partida.setFase(Fase.COLOCACION); // Ajusta según tu enum
        when(partidaRepository.findById(partidaId)).thenReturn(Optional.of(partida));

        // Act
        turnoDTO resultado = turnoService.getHistorialTurnos(partidaId);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getRonda());
        assertEquals(1, resultado.getTurno());
        assertEquals(Fase.COLOCACION, resultado.getFase());
        verify(partidaRepository, times(1)).findById(partidaId);
    }

    @Test
    void testGetHistorialTurnos_ConValoresMaximos_DeberiaRetornarTurnoDTOCorrectamente() {
        // Arrange
        partida.setRonda(10);
        partida.setTurno(50);
        partida.setFase(Fase.REAGRUPACION); // Ajusta según tu enum
        when(partidaRepository.findById(partidaId)).thenReturn(Optional.of(partida));

        // Act
        turnoDTO resultado = turnoService.getHistorialTurnos(partidaId);

        // Assert
        assertNotNull(resultado);
        assertEquals(10, resultado.getRonda());
        assertEquals(50, resultado.getTurno());
        assertEquals(Fase.REAGRUPACION, resultado.getFase());
        verify(partidaRepository, times(1)).findById(partidaId);
    }

    @Test
    void testGetTurnoActual_ConDiferentesIdsPartida_DeberiaFuncionarCorrectamente() {
        // Arrange
        Integer otroPartidaId = 999;
        Partida otraPartida = new Partida();
        otraPartida.setId(otroPartidaId);
        otraPartida.setTurno(15);

        when(partidaRepository.findById(otroPartidaId)).thenReturn(Optional.of(otraPartida));

        // Act
        Integer resultado = turnoService.getTurnoActual(otroPartidaId);

        // Assert
        assertEquals(15, resultado);
        verify(partidaRepository, times(1)).findById(otroPartidaId);
    }

    @Test
    void testGetHistorialTurnos_ConDiferentesFases_DeberiaRetornarCorrectamente() {
        // Arrange - Testa diferentes fases si las tienes
        partida.setFase(Fase.COLOCACION);
        when(partidaRepository.findById(partidaId)).thenReturn(Optional.of(partida));

        // Act
        turnoDTO resultado = turnoService.getHistorialTurnos(partidaId);

        // Assert
        assertEquals(Fase.COLOCACION, resultado.getFase());
        verify(partidaRepository, times(1)).findById(partidaId);
    }
}