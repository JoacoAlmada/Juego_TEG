package com.teg.teg_juego.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Random;

@JsonFormat(shape = JsonFormat.Shape.STRING)

public enum Simbolo {
    INFANTERIA,
    CABALLERIA,
    ARTILLERIA;
}
