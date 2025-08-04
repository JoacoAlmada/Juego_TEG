package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.TurnoService;
import com.teg.teg_juego.model.DTO.turnoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/Turno")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;

    @GetMapping("/{idPartida}")
    public ResponseEntity<Integer> getTurnoActual(@PathVariable Integer idPartida)
    {
        return ResponseEntity.ok(turnoService.getTurnoActual(idPartida));
    }

    @GetMapping("/{idPartida}/historial")
    public ResponseEntity<turnoDTO> getHistorialTurnos(@PathVariable Integer idPartida)
    {
        return ResponseEntity.ok(turnoService.getHistorialTurnos(idPartida));
    }
}
