package com.teg.teg_juego.controllers;

import com.teg.teg_juego.Service.JugadorService;
import com.teg.teg_juego.model.DTO.jugadorDTO;
import com.teg.teg_juego.model.DTO.jugadorPartidaDTO;
import com.teg.teg_juego.model.entities.Jugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.util.List;

@RestController
@RequestMapping("api/Jugador")
public class JugadorController {
    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public List<jugadorDTO> getAll() {
        return jugadorService.getAllJugadores();
    }

    @GetMapping("/{id}")
    public jugadorDTO getById(@PathVariable Integer id) {
        return jugadorService.getJugadorById(id);
    }

    @PostMapping
    public ResponseEntity<jugadorDTO> createJugador(@RequestBody jugadorDTO jugadorDTO) {
        jugadorDTO createdJugador = jugadorService.createJugador(jugadorDTO);
        return ResponseEntity.ok(createdJugador);
    }

    @PutMapping("/{id}")
    public jugadorDTO update(@PathVariable Integer id, @RequestBody Jugador jugador) {
        return jugadorService.updateJugador(id, jugador);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        jugadorService.deleteJugador(id);
    }

    @GetMapping("/partida/{id}")
    public List<jugadorDTO> getByPartida(@PathVariable Integer id) {
        return jugadorService.getJugadoresDePartida(id);
    }

    @PostMapping(value = "/partida/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public jugadorDTO addJugadorAPartida(@PathVariable Integer id, @RequestBody Jugador jugador) {
        return jugadorService.agregarJugadorAPartida(id, jugador);
    }

    @PostMapping("/Partida")
    public void crearJugadorEnPartida(@RequestBody jugadorPartidaDTO jugadorPartidaDTO) {
        jugadorService.crearJugadorEnPartida(jugadorPartidaDTO);
    }
}
