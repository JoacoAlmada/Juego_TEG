package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.*;
import com.teg.teg_juego.model.DTO.jugadorDTO;
import com.teg.teg_juego.model.DTO.partidaDTO;
import com.teg.teg_juego.model.entities.*;
import com.teg.teg_juego.model.enums.EstadoJugador;
import com.teg.teg_juego.model.enums.EstadoPartida;
import com.teg.teg_juego.model.mappers.PartidaMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartidaService {
    @Autowired
    private partidaRepository PartidaRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private botRepository BotRepository;
    @Autowired
    private jugadorRepository JugadorRepository;
    @Autowired
    private objetivoRepository objetivoRepository;
    @Autowired
    private paisRepository paisRepository;

    public List<partidaDTO> getAllPartidas() {
        List<Partida> partidas = PartidaRepository.findAll();
        return partidas.stream()
                .map(PartidaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public partidaDTO getPartidaById(Integer id) {
        Partida partida = PartidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        return PartidaMapper.toDTO(partida);
    }

    public Integer crearPartida(Partida partida) {
        Partida saved = PartidaRepository.save(partida);
        System.out.println("ID generado: " + saved.getId());
        return saved.getId();
    }

    public partidaDTO actualizarPartida(Integer id, Partida nueva) {
        Partida partida = PartidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        partida.setCantidadJugadores(nueva.getCantidadJugadores());
        partida.setEstadoPartida(nueva.getEstadoPartida());
        partida.setFase(nueva.getFase());
        partida.setTurno(nueva.getTurno());
        partida.setRonda(nueva.getRonda());

        return modelMapper.map(PartidaRepository.save(partida), partidaDTO.class);
    }

    @Transactional
    public void avanzarFase(Integer id) {
        Partida partida = PartidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        System.out.println("[AvanzarFase] Fase antes: " + partida.getFase());
        partida.pasarFase();
        System.out.println("[AvanzarFase] Fase después: " + partida.getFase());

        PartidaRepository.save(partida);
    }

    public void pasarTurno(Integer id) {
        Partida partida = PartidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        Jugador jugadorActual = partida.getJugadores().get(partida.getTurno());

        if (partida.getRonda() != null && partida.getRonda() >= 3) {
            boolean objetivoCumplido = jugadorActual.getObjetivo().verificarObjetivo(jugadorActual, partida.getJugadores());

            if (objetivoCumplido) {
                partida.terminarPartida(jugadorActual);
                PartidaRepository.save(partida);
                return;
            }

        }

        partida.pasarTurno();
        PartidaRepository.save(partida);
    }

    public void guardarPartida(Integer id) {
        Optional<Partida> optional = PartidaRepository.findById(id);//optional sirve para representar que el valor que trae puede estar o no
        if (optional.isEmpty()) {
            throw new RuntimeException("Partida no encontrada para guardar");
        }
        Partida partida = optional.get();
        partida.guardarPartida();
        PartidaRepository.save(partida);
        System.out.println("Estado de la Partida " + id + " guardado correctamente.");
    }


    public jugadorDTO obtenerGanador(Integer partidaId) {
        Optional<Partida> partidaOptional = PartidaRepository.findById(partidaId);
        if (partidaOptional.isPresent()) {
            Partida partida = partidaOptional.get();
            if (partida.getEstadoPartida() == EstadoPartida.TERMINADA) {
                Jugador ganador = partida.getGanadorTemporal();
                return modelMapper.map(ganador, jugadorDTO.class);
            }
        }
        return null;
    }


    public partidaDTO cargarPartida(Integer id) {
        Partida partida = PartidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        //partida.iniciarPartida();

        partidaDTO dto = modelMapper.map(partida, partidaDTO.class);

        int turnoActual = partida.getTurno();
        List<Jugador> jugadores = partida.getJugadores();

        if (turnoActual >= 0 && turnoActual < jugadores.size()) {
            Jugador jugadorActual = jugadores.get(turnoActual);
            dto.setJugadorActualId(jugadorActual.getId());
            dto.setJugadorActualNombre(jugadorActual.getNombreJ());
        } else {
            dto.setJugadorActualId(null);
            dto.setJugadorActualNombre(null);
        }

        return dto;
    }

    @Transactional
    public void iniciarPartida(Integer id) {
        Partida partida = PartidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        List<Objetivo> todosLosObjetivos = objetivoRepository.findAll();
        List<Pais> todosLosPaises = paisRepository.findAll();

        partida.iniciarPartida();
        partida.repartirPaises(todosLosPaises);
        partida.repartirObjetivos(todosLosObjetivos);
        partida.repartirEjercitos();

        List<Jugador> jugadores = new ArrayList<>(partida.getJugadores()); // copia mutable

        Optional<Jugador> primerHumano = jugadores.stream()
                .filter(j -> !(j instanceof Bot))
                .findFirst();

        primerHumano.ifPresent(humano -> {
            jugadores.remove(humano);
            jugadores.add(0, humano);
        });

        partida.setJugadores(jugadores);

        partida.setTurno(0);
        partida.setCantidadJugadores(jugadores.size());

        jugadores.forEach(jugador -> jugador.setEstadoJ(EstadoJugador.ACTIVO));

        PartidaRepository.save(partida);
    }
    private Pais buscarPaisPorNombre(List<Pais> paises, String nombreBuscado) {
        return paises.stream()
                .filter(p -> p.getNombre().trim().equalsIgnoreCase(nombreBuscado.trim()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("País no encontrado: " + nombreBuscado));
    }

    public void colocar(Integer id, String pais, Integer tropas) {
        Partida partida = PartidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        Jugador jugadorActual = partida.getJugadores().get(partida.getTurno());

        Pais paisEncontrado = buscarPaisPorNombre(jugadorActual.getPaises(), pais);

        jugadorActual.colocar(paisEncontrado, tropas);

        PartidaRepository.save(partida);
    }

    public ResultadoAtaque atacar(Integer id, String origen, String destino) {
        Partida partida = PartidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        Jugador jugadorActual = partida.getJugadores().get(partida.getTurno());

        Pais paisOrigen = buscarPaisPorNombre(jugadorActual.getPaises(), origen);
        Pais paisDestino = null;
        for (Jugador jugador : partida.getJugadores()) {
            if (!jugador.equals(jugadorActual)) {
                try {
                    paisDestino = buscarPaisPorNombre(jugador.getPaises(), destino);
                    break;
                } catch (RuntimeException e) {
                }
            }
        }

        if (paisDestino == null) {
            throw new RuntimeException("País destino no encontrado: " + destino);
        }

        ResultadoAtaque resultadoAtaque = jugadorActual.atacar(paisOrigen, paisDestino);

        if (partida.getRonda() >= 3) {
            boolean objetivoCumplido = jugadorActual.getObjetivo().verificarObjetivo(jugadorActual, partida.getJugadores());
            if (objetivoCumplido) {
                partida.terminarPartida(jugadorActual);
            }
        }

        PartidaRepository.save(partida);

        return resultadoAtaque;
    }

    public void mover(Integer id, String origen, String destino, int tropas) {
        Partida partida = PartidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        Jugador jugadorActual = partida.getJugadores().get(partida.getTurno());

        Pais paisOrigen = buscarPaisPorNombre(jugadorActual.getPaises(), origen);
        Pais paisDestino = buscarPaisPorNombre(jugadorActual.getPaises(), destino);

        jugadorActual.mover(paisOrigen, paisDestino, tropas);

        PartidaRepository.save(partida);
    }

    public void iniciarRonda(Integer id){

        Partida partida = PartidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        partida.iniciarRonda();
        PartidaRepository.save(partida);
    }

    public partidaDTO reanudarPartida() {
        List<Partida> partidas = PartidaRepository.findAll();

        if (partidas.isEmpty()) {
            throw new RuntimeException("No hay partidas registradas.");
        }

        Partida ultimaPartida = partidas.get(partidas.size() - 1);

        if (!ultimaPartida.getEstadoPartida().equals(EstadoPartida.EN_JUEGO)) {
            throw new RuntimeException("La última partida no está en estado EN_JUEGO.");
        }

        return PartidaMapper.toDTO(ultimaPartida);
    }
}


