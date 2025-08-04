package com.teg.teg_juego.model.DTO;

import com.teg.teg_juego.model.entities.Jugador;
import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.EstadoJugador;
import com.teg.teg_juego.model.enums.TipoJugador;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class jugadorDTOTest {

    @Test
    public void testConstructorSinParametrosYSetters(){

        jugadorDTO dto = new jugadorDTO();

        Integer id = 1;
        String nombre = "Juan";
        Color color = Color.ROJO;
        Integer fichas = 5;
        TipoJugador tipoJugador = TipoJugador.HUMANO;
        EstadoJugador estado = EstadoJugador.ACTIVO;

        List<paisDTO> paises = new ArrayList<>();
        paises.add(new paisDTO());
        paises.add(new paisDTO());

        objetivoDTO objetivo = new objetivoDTO();

        List<tarjetaDTO> tarjetas = new ArrayList<>();
        tarjetas.add(new tarjetaDTO());


        dto.setId(id);
        dto.setNombre(nombre);
        dto.setColor(color);
        dto.setFichas(fichas);
        dto.setTipoJugador(tipoJugador);
        dto.setEstado(estado);
        dto.setPaises(paises);
        dto.setObjetivo(objetivo);
        dto.setTarjetas(tarjetas);

        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombre());
        assertEquals(color, dto.getColor());
        assertEquals(fichas, dto.getFichas());
        assertEquals(tipoJugador, dto.getTipoJugador());
        assertEquals(estado, dto.getEstado());
        assertEquals(paises, dto.getPaises());
        assertEquals(objetivo, dto.getObjetivo());
        assertEquals(tarjetas, dto.getTarjetas());
    }

    @Test
    public void testConstructorConTodosLosParametros() {
        // Arrange
        Integer id = 2;
        String nombre = "Maria";
        Color color = Color.AZUL;
        Integer fichas = 10;
        TipoJugador tipoJugador = TipoJugador.BOT;
        EstadoJugador estado = EstadoJugador.ELIMINADO;

        List<paisDTO> paises = new ArrayList<>();
        paises.add(new paisDTO());
        paises.add(new paisDTO());

        objetivoDTO objetivo = new objetivoDTO();

        List<tarjetaDTO> tarjetas = new ArrayList<>();
        tarjetas.add(new tarjetaDTO());
        tarjetas.add(new tarjetaDTO());

        // Act
        jugadorDTO dto = new jugadorDTO(id, nombre, color, fichas, tipoJugador, estado, paises, objetivo, tarjetas);

        // Assert
        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombre());
        assertEquals(color, dto.getColor());
        assertEquals(fichas, dto.getFichas());
        assertEquals(tipoJugador, dto.getTipoJugador());
        assertEquals(estado, dto.getEstado());
        assertEquals(paises, dto.getPaises());
        assertEquals(objetivo, dto.getObjetivo());
        assertEquals(tarjetas, dto.getTarjetas());
    }

    @Test
    public void testConstructorConJugador() {
        // Arrange - Creamos un objeto Jugador real (no mock)
        Jugador jugador = new Jugador();
        jugador.setId(3);
        jugador.setNombreJ("Pedro");
        jugador.setColor(Color.VERDE);
        jugador.setFichasJ(15);
        jugador.setTipoJ(TipoJugador.HUMANO);
        jugador.setEstadoJ(EstadoJugador.ACTIVO);

        // Act
        jugadorDTO dto = new jugadorDTO(jugador);

        // Assert
        assertEquals(jugador.getId(), dto.getId());
        assertEquals(jugador.getNombreJ(), dto.getNombre());
        assertEquals(jugador.getColor(), dto.getColor());
        assertEquals(jugador.getFichasJ(), dto.getFichas());
        assertEquals(jugador.getTipoJ(), dto.getTipoJugador());
        assertEquals(jugador.getEstadoJ(), dto.getEstado());
    }
}