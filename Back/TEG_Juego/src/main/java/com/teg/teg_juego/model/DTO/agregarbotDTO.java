package com.teg.teg_juego.model.DTO;


import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.DificultadBot;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class agregarbotDTO {
    private DificultadBot dificultad;
    private int cantidad;
    private List<Color> colores;
}
