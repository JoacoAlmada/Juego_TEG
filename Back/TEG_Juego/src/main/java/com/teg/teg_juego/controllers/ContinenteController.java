package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.ContinenteService;
import com.teg.teg_juego.model.entities.Continente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/Continente")
public class ContinenteController {

    @Autowired
    private ContinenteService continenteService;

    @GetMapping
    public List<Continente> getAll() {
        return continenteService.getAll();
    }
}
