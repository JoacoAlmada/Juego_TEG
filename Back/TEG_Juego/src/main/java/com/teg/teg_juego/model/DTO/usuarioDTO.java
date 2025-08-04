package com.teg.teg_juego.model.DTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class usuarioDTO {
    private Integer id;
    private String nombre;
    private String contrasenia;
    private Integer nivel;
}
