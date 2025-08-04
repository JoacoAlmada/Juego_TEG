package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.PaisService;
import com.teg.teg_juego.model.DTO.paisDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/Pais")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @GetMapping
    public List<paisDTO> getAll() {
        return paisService.getAll();
    }

    @GetMapping("/{id}")
    public paisDTO getById(@PathVariable Integer id) {
        return paisService.getById(id);
    }

    @GetMapping("/{nombre}/vecinos")
    public List<paisDTO> getVecinosPorNombre(@PathVariable String nombre) {
        return paisService.getVecinosPorNombre(nombre);
    }

    @GetMapping("/{nombre}/tropas")
    public Integer getTropasPorNombre(@PathVariable String nombre) {
        return paisService.getTropasPorNombre(nombre);
    }
}
