package com.teg.teg_juego.model.DTO;


import com.teg.teg_juego.model.entities.Pais;
import com.teg.teg_juego.model.enums.Color;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class paisDTO {
    private Integer id;
    private String nombre;
    private Color color;
    private Integer ejercito;
    private String continente;

    public paisDTO(Pais pais) {
        this.id = pais.getId();
        this.nombre = pais.getNombre();
        this.color = pais.getJugador().getColor();
        this.ejercito = pais.getEjercito();
        this.continente = pais.getContinente().getNombre();
    }
}
