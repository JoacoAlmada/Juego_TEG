package com.teg.teg_juego.model.DTO;

import com.teg.teg_juego.model.enums.TipoObjetivo;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class objetivoDTO {
    private Integer id;
    private String descripcion;
    private TipoObjetivo tipo;
    private Boolean estado;
}
