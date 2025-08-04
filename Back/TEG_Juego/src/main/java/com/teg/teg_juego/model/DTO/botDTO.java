package com.teg.teg_juego.model.DTO;


import com.teg.teg_juego.model.entities.Bot;
import com.teg.teg_juego.model.enums.DificultadBot;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class botDTO extends jugadorDTO {
    private DificultadBot dificultad;

    public botDTO(Bot bot) {
        super(bot);
        this.dificultad = bot.getDificultad();
    }
}


