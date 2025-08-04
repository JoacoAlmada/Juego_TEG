package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.objetivoRepository;
import com.teg.teg_juego.model.DTO.objetivoDTO;
import com.teg.teg_juego.model.entities.Objetivo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObjetivoService {

    @Autowired
    private objetivoRepository ObjetivoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<objetivoDTO> getAllObjetivos() {
        return ObjetivoRepository.findAll().stream().map(obj -> modelMapper.map(obj, objetivoDTO.class)).collect(Collectors.toList());
    }

    public objetivoDTO getObjetivoById(Integer id) {
        Objetivo obj = ObjetivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objetivo no encontrado"));
        return modelMapper.map(obj, objetivoDTO.class);
    }

}
