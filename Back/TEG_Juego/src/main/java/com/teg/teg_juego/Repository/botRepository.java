package com.teg.teg_juego.Repository;


import com.teg.teg_juego.model.entities.Bot;
import com.teg.teg_juego.model.enums.DificultadBot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface botRepository extends JpaRepository<Bot, Integer> {
    List<Bot> findByDificultad(DificultadBot dificultad);

}
