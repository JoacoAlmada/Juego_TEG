package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.paisRepository;
import com.teg.teg_juego.model.DTO.paisDTO;
import com.teg.teg_juego.model.entities.Pais;
import com.teg.teg_juego.model.mappers.PaisMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaisService {

    @Autowired
    public paisRepository PaisRepository;
    @Autowired
    public ModelMapper modelMapper;

    public List<paisDTO> getAll() {
        List<Pais> paises = PaisRepository.findAll(); // o paisService.getAll()
        return paises.stream()
                .map(PaisMapper::toDTO)
                .collect(Collectors.toList());
    }

    public paisDTO getById(Integer id) {
        Pais pais = PaisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("País no encontrado"));
        return PaisMapper.toDTO(pais);
    }

    public List<paisDTO> getVecinosPorNombre(String nombre) {
        Pais pais = PaisRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("País no encontrado"));

        return pais.getVecinos().stream()
                .map(v -> modelMapper.map(v, paisDTO.class))
                .collect(Collectors.toList());
    }

    public Integer getTropasPorNombre(String nombre) {
        Pais pais = PaisRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("País no encontrado"));

        return pais.getEjercito();
    }
}
