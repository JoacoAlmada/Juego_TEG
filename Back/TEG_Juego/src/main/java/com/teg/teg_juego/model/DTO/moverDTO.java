package com.teg.teg_juego.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class moverDTO {
    private String origen;
    private String destino;
    private Integer tropas;
}
