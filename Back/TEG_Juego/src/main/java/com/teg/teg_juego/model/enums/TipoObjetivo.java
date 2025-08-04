package com.teg.teg_juego.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)

public enum TipoObjetivo {
    CONQUISTA ,
    ELIMINAR

}
