package com.teg.teg_juego.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CanjeRequestDTO {
    private int numeroCanje;
    private List<Integer> idsTarjetasSeleccionadas;
}