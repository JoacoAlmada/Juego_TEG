package com.teg.teg_juego.controllers;



import com.teg.teg_juego.Service.BotService;
import com.teg.teg_juego.model.DTO.agregarbotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/Bot")
public class BotController {

    @Autowired
    private BotService botService;

    @PostMapping("/turno/{idPartida}")
    public ResponseEntity<Map<String, String>> ejecutarTurnosBots(@PathVariable int idPartida) {
        botService.ejecutarTurnosBots(idPartida);
        return ResponseEntity.ok(Map.of("mensaje", "Turnos de bots ejecutados hasta llegar a jugador humano o fin de partida."));
    }

    @PostMapping("/agregar/{idPartida}")
    public ResponseEntity<String> agregarBots(
            @PathVariable int idPartida,
            @RequestBody agregarbotDTO request) {
        botService.agregarBotsAlaPartida(idPartida, request.getDificultad(), request.getCantidad(), request.getColores());
        return ResponseEntity.ok("Bots agregados correctamente a la partida.");
    }
}
