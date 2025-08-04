package com.teg.teg_juego.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class jugadorPartidaDTO {
    private List<jugadorDTO> jugadores;
    private Integer partidaId;
}
