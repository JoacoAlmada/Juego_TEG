package com.teg.teg_juego.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;


import com.teg.teg_juego.Repository.partidaRepository;
import com.teg.teg_juego.model.DTO.jugadorDTO;
import com.teg.teg_juego.model.DTO.partidaDTO;
import com.teg.teg_juego.model.entities.*;
import com.teg.teg_juego.model.enums.EstadoPartida;
import com.teg.teg_juego.model.enums.Fase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PartidaServiceTest {

    @Mock
    private partidaRepository partidaRepository;

    @Mock
    private ModelMapper modelMapper;



    @InjectMocks
    private PartidaService partidaService;

    private Partida partida;
    private partidaDTO partidaDTO;

    private Jugador jugador1;
    private Jugador jugador2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        partida = new Partida();
        partida.setId(1);
        partida.setCantidadJugadores(2);
        partida.setEstadoPartida(EstadoPartida.EN_JUEGO);
        partida.setFase(Fase.COLOCACION);
        partida.setTurno(0);
        partida.setRonda(3);


        jugador1 = mock(Jugador.class);
        jugador2 = mock(Jugador.class);


        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        partida.setJugadores(jugadores);


        partidaDTO = new partidaDTO();
        partidaDTO.setId(1);
        partidaDTO.setCantidadJugadores(2);
        partidaDTO.setEstado(EstadoPartida.EN_JUEGO);
        partidaDTO.setFase(Fase.COLOCACION);
        partidaDTO.setTurno(0);
        partidaDTO.setRonda(3);
    }

    @Test
    void testGetAllPartidas() {
        when(partidaRepository.findAll()).thenReturn(List.of(partida));

        List<partidaDTO> resultado = partidaService.getAllPartidas();

        assertNotNull(resultado);
        verify(partidaRepository).findAll();
    }

    @Test
    void testGetPartidaById() {
        when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));


        partidaDTO dto = partidaService.getPartidaById(1);

        assertNotNull(dto);
        verify(partidaRepository).findById(1);
    }

    @Test
    void testCrearPartida() {
        when(partidaRepository.save(partida)).thenReturn(partida);

        Integer id = partidaService.crearPartida(partida);

        assertEquals(partida.getId(), id);
        verify(partidaRepository).save(partida);
    }

    @Test
    void testActualizarPartida() {
        Partida nueva = new Partida();
        nueva.setCantidadJugadores(3);
        nueva.setEstadoPartida(EstadoPartida.EN_JUEGO);
        nueva.setFase(Fase.ATAQUE);
        nueva.setTurno(1);
        nueva.setRonda(2);

        when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));
        when(partidaRepository.save(any(Partida.class))).thenReturn(partida);
        when(modelMapper.map(any(Partida.class), eq(partidaDTO.class))).thenReturn(partidaDTO);

        partidaDTO dto = partidaService.actualizarPartida(1, nueva);

        assertNotNull(dto);
        verify(partidaRepository).save(any(Partida.class));
    }

    @Test
    void testAvanzarFase() {

        Partida spyPartida = spy(partida);
        when(partidaRepository.findById(1)).thenReturn(Optional.of(spyPartida));

        partidaService.avanzarFase(1);

        verify(spyPartida).pasarFase();
        verify(partidaRepository).save(spyPartida);
    }

    @Test
    void testPasarTurno_ConObjetivoCumplido() {
        Partida spyPartida = spy(partida);
        List<Jugador> jugadoresMock = List.of(jugador1, jugador2);

        doReturn(jugadoresMock).when(spyPartida).getJugadores();
        doReturn(0).when(spyPartida).getTurno();

        when(partidaRepository.findById(1)).thenReturn(Optional.of(spyPartida));

        Objetivo objetivoMock = mock(Objetivo.class);
        when(jugador1.getObjetivo()).thenReturn(objetivoMock);
        when(objetivoMock.verificarObjetivo(jugador1, jugadoresMock)).thenReturn(true);

        partidaService.pasarTurno(1);

        verify(partidaRepository).save(spyPartida);
        verify(spyPartida).terminarPartida(jugador1);
    }

    @Test
    void testPasarTurno_SinObjetivoCumplido() {
        Partida spyPartida = spy(partida);
        List<Jugador> jugadoresMock = List.of(jugador1, jugador2);

        doReturn(jugadoresMock).when(spyPartida).getJugadores();
        doReturn(0).when(spyPartida).getTurno();

        when(partidaRepository.findById(1)).thenReturn(Optional.of(spyPartida));

        Objetivo objetivoMock = mock(Objetivo.class);
        when(jugador1.getObjetivo()).thenReturn(objetivoMock);
        when(objetivoMock.verificarObjetivo(jugador1, jugadoresMock)).thenReturn(false);

        partidaService.pasarTurno(1);

        verify(partidaRepository).save(spyPartida);
        verify(spyPartida).pasarTurno();
    }

    @Test
    void testGuardarPartida_Existente() {
        Partida spyPartida = spy(partida);
        when(partidaRepository.findById(1)).thenReturn(Optional.of(spyPartida));

        partidaService.guardarPartida(1);

        verify(spyPartida).guardarPartida();
        verify(partidaRepository).save(spyPartida);
    }

    @Test
    void testGuardarPartida_NoExistente() {
        when(partidaRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> partidaService.guardarPartida(99));
        assertEquals("Partida no encontrada para guardar", ex.getMessage());
    }

    @Test
    void testObtenerGanador_ConGanador() {
        partida.setEstadoPartida(EstadoPartida.TERMINADA);
        Jugador ganador = mock(Jugador.class);
        partida.setGanadorTemporal(ganador);

        jugadorDTO dtoGanador = new jugadorDTO();
        when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));
        when(modelMapper.map(ganador, jugadorDTO.class)).thenReturn(dtoGanador);

        jugadorDTO resultado = partidaService.obtenerGanador(1);

        assertNotNull(resultado);
    }

    @Test
    void testObtenerGanador_SinGanador() {
        partida.setEstadoPartida(EstadoPartida.EN_JUEGO);
        when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));

        jugadorDTO resultado = partidaService.obtenerGanador(1);

        assertNull(resultado);
    }



    @Test
    void testColocar() {
        List<Pais> paisesJugador = new ArrayList<>();
        Pais paisMock = mock(Pais.class);
        paisesJugador.add(paisMock);

        List<Jugador> jugadores = List.of(jugador1);

        Partida spyPartida = spy(partida);

        when(partidaRepository.findById(1)).thenReturn(Optional.of(spyPartida));
        when(partidaRepository.save(spyPartida)).thenReturn(spyPartida);


        doReturn(jugadores).when(spyPartida).getJugadores();
        doReturn(0).when(spyPartida).getTurno();

        when(jugador1.getPaises()).thenReturn(paisesJugador);
        when(paisMock.getNombre()).thenReturn("Argentina");

        doNothing().when(jugador1).colocar(any(Pais.class), eq(5));

        partidaService.colocar(1, "Argentina", 5);

        verify(jugador1).colocar(paisMock, 5);
        verify(partidaRepository).save(spyPartida);
    }

    @Test
    void testAtacar() {
        Pais paisOrigen = mock(Pais.class);
        Pais paisDestino = mock(Pais.class);

        Partida spyPartida = spy(partida);
        List<Jugador> jugadores = List.of(jugador1, jugador2);

        when(partidaRepository.findById(1)).thenReturn(Optional.of(spyPartida));

        doReturn(jugadores).when(spyPartida).getJugadores();
        doReturn(0).when(spyPartida).getTurno();

        when(jugador1.getPaises()).thenReturn(List.of(paisOrigen));
        when(paisOrigen.getNombre()).thenReturn("Chile");

        when(jugador2.getPaises()).thenReturn(List.of(paisDestino));
        when(paisDestino.getNombre()).thenReturn("Argentina");

        when(jugador1.atacar(paisOrigen, paisDestino)).thenReturn(new ResultadoAtaque());

        spyPartida.setRonda(3);

        Objetivo objetivoMock = mock(Objetivo.class);
        when(jugador1.getObjetivo()).thenReturn(objetivoMock);
        when(objetivoMock.verificarObjetivo(jugador1, jugadores)).thenReturn(false);

        when(partidaRepository.save(spyPartida)).thenReturn(spyPartida);

        ResultadoAtaque resultado = partidaService.atacar(1, "Chile", "Argentina");

        assertNotNull(resultado);
        verify(partidaRepository).save(spyPartida);
    }

    @Test
    void testMover() {
        Pais origen = mock(Pais.class);
        Pais destino = mock(Pais.class);

        Partida spyPartida = spy(partida);
        List<Jugador> jugadores = List.of(jugador1);

        when(partidaRepository.findById(1)).thenReturn(Optional.of(spyPartida));

        doReturn(jugadores).when(spyPartida).getJugadores();
        doReturn(0).when(spyPartida).getTurno();

        when(jugador1.getPaises()).thenReturn(List.of(origen, destino));
        when(origen.getNombre()).thenReturn("Chile");
        when(destino.getNombre()).thenReturn("Argentina");

        doNothing().when(jugador1).mover(origen, destino, 4);
        when(partidaRepository.save(spyPartida)).thenReturn(spyPartida);

        partidaService.mover(1, "Chile", "Argentina", 4);

        verify(jugador1).mover(origen, destino, 4);
        verify(partidaRepository).save(spyPartida);
    }

    @Test
    void testIniciarRonda() {
        Partida spyPartida = spy(partida);
        when(partidaRepository.findById(1)).thenReturn(Optional.of(spyPartida));

        partidaService.iniciarRonda(1);

        verify(spyPartida).iniciarRonda();
        verify(partidaRepository).save(spyPartida);
    }
}
