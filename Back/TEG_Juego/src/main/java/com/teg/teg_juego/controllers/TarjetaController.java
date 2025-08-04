package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.TarjetaService;
import com.teg.teg_juego.model.DTO.tarjetaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Tarjeta")
public class TarjetaController {
    @Autowired
    private TarjetaService tarjetaService;

    @GetMapping
    public List<tarjetaDTO> getAll() {
        return tarjetaService.getAll();
    }

    @GetMapping("/jugador/{id}")
    public List<tarjetaDTO> getJugadorTarjetas(@PathVariable Integer id) {
        return tarjetaService.getTarjetasJugador(id);
    }

}
