package com.teg.teg_juego.model.mappers;


import com.teg.teg_juego.model.DTO.paisDTO;
import com.teg.teg_juego.model.entities.Pais;

public class PaisMapper {

    public static paisDTO toDTO(Pais p) {
        paisDTO pDTO = new paisDTO();
        pDTO.setId(p.getId());
        pDTO.setNombre(p.getNombre());
        pDTO.setColor(p.getJugador().getColor());
        pDTO.setEjercito(p.getEjercito());
        if (p.getContinente() != null) {
            pDTO.setContinente(p.getContinente().getNombre());
        }
        return pDTO;
    }
}
