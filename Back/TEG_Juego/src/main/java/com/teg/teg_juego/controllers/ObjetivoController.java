package com.teg.teg_juego.controllers;


import com.teg.teg_juego.Service.ObjetivoService;
import com.teg.teg_juego.model.DTO.objetivoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/Objetivo")
public class ObjetivoController {

    @Autowired
    private ObjetivoService objetivoService;

    @GetMapping
    public List<objetivoDTO> getAll() {
        return objetivoService.getAllObjetivos();
    }

    @GetMapping("/{id}")
    public objetivoDTO getById(@PathVariable Integer id) {
        return objetivoService.getObjetivoById(id);
    }
}
