package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.PartidaService;
import com.teg.teg_juego.model.DTO.*;
import com.teg.teg_juego.model.entities.Partida;
import com.teg.teg_juego.model.entities.ResultadoAtaque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/Partida")
public class PartidaController {
    @Autowired
    private PartidaService partidaService;

    @GetMapping
    public List<partidaDTO> getAll() {
        return partidaService.getAllPartidas();
    }

    @GetMapping("/{id}")
    public partidaDTO getById(@PathVariable Integer id) {
        return partidaService.getPartidaById(id);
    }

    @GetMapping("/reanudar")
    public partidaDTO reanudarPartida() {
        return partidaService.reanudarPartida();
    }

    @PostMapping
    public Integer crear(@RequestBody Partida partida) {
        return partidaService.crearPartida(partida);
    }

    @PutMapping("/{id}")
    public partidaDTO actualizar(@PathVariable Integer id, @RequestBody Partida partida) {
        return partidaService.actualizarPartida(id, partida);
    }

    @PutMapping("/{id}/fase")
    public void avanzarFase(@PathVariable Integer id) {
        partidaService.avanzarFase(id);
    }

    @PutMapping("/{id}/turno")
    public void pasarTurno(@PathVariable Integer id) {
        partidaService.pasarTurno(id);
    }

    @PutMapping("/{id}/guardar")
    public void guardar(@PathVariable Integer id) {
        partidaService.guardarPartida(id);
    }

    @GetMapping("/{id}/cargar")
    public partidaDTO cargar(@PathVariable Integer id) {
        return partidaService.cargarPartida(id);
    }

    @PostMapping("/{id}/iniciar")
    public void iniciar(@PathVariable Integer id) {
        partidaService.iniciarPartida(id);
    }

    @PostMapping("/{id}/colocar")
    public void colocar(@PathVariable Integer id, @RequestBody colocarDTO colocarDTO) {
        partidaService.colocar(id, colocarDTO.getPais(), colocarDTO.getTropas());
    }

    @PostMapping("/{id}/atacar")
    public ResultadoAtaque atacar(@PathVariable Integer id, @RequestBody atacarDTO atacarDTO) {
        return partidaService.atacar(id, atacarDTO.getOrigen(), atacarDTO.getDestino());
    }

    @PostMapping("/{id}/mover")
    public void mover(@PathVariable Integer id, @RequestBody moverDTO moverDTO) {
        partidaService.mover(id, moverDTO.getOrigen(), moverDTO.getDestino(), moverDTO.getTropas());
    }

    @PutMapping("/{id}/ronda")
    public void iniciarRonda(@PathVariable Integer id) {
        partidaService.iniciarRonda(id);
    }

    @GetMapping("/partidas/{id}/ganador")
    public ResponseEntity<jugadorDTO> obtenerGanador(@PathVariable Integer id) {
        jugadorDTO ganador = partidaService.obtenerGanador(id);
        if (ganador != null) {
            return ResponseEntity.ok(ganador);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
