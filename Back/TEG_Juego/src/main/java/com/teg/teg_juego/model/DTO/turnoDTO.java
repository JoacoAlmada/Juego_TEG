package com.teg.teg_juego.model.DTO;

import com.teg.teg_juego.model.enums.Fase;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class turnoDTO {
    private Integer ronda;
    private Integer turno;
    private Fase fase;
}
