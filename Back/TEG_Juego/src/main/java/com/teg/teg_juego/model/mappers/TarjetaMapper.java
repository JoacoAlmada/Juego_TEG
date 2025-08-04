package com.teg.teg_juego.model.mappers;


import com.teg.teg_juego.model.DTO.tarjetaDTO;
import com.teg.teg_juego.model.entities.TarjetaPais;

public class TarjetaMapper {

    public static tarjetaDTO toDTO(TarjetaPais tarjeta) {
        if (tarjeta == null) return null;

        tarjetaDTO dto = new tarjetaDTO();
        dto.setId(tarjeta.getNumber());
        dto.setSimbolo(tarjeta.getSimbolo());

        if (tarjeta.getPais() != null) {
            dto.setPais(tarjeta.getPais().getNombre());
        }

        return dto;
    }
}
