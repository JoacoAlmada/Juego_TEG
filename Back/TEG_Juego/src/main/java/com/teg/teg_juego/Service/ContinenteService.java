package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.continenteRepository;
import com.teg.teg_juego.model.entities.Continente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContinenteService {

    @Autowired
    private continenteRepository ContinenteRepository;

    public List<Continente> getAll() {
        return ContinenteRepository.findAll();
    }
}
