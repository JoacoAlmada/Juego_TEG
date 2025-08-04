package com.teg.teg_juego.model.entities;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoAtaque {
    private String resultado; // "ataque" o "conquista"
    private List<Integer> dadosAtaque;
    private List<Integer> dadosDefensa;
}
