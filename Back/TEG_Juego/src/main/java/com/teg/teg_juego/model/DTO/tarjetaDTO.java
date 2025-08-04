package com.teg.teg_juego.model.DTO;

import com.teg.teg_juego.model.enums.Simbolo;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class tarjetaDTO {
    private  Integer id;
    private Simbolo simbolo;
    private String pais;
}
