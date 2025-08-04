package com.teg.teg_juego.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CanjearDTO {
    private boolean exito;
    private int tropasObtenidas;
    private String mensaje;
}
