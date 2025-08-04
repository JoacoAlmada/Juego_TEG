package com.teg.teg_juego.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)

public enum EstadoPartida {

    EN_JUEGO,
    GUARDADA,
    TERMINADA

}
