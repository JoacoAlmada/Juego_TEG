package com.teg.teg_juego.model.mappers;


import com.teg.teg_juego.model.DTO.objetivoDTO;
import com.teg.teg_juego.model.entities.Objetivo;

public class ObjetivoMapper {

    public static objetivoDTO toDTO(Objetivo obj) {
        if (obj == null) return null;
        objetivoDTO dto = new objetivoDTO();
        dto.setId(obj.getId());
        dto.setDescripcion(obj.getDescripcion());
        return dto;
    }
}
