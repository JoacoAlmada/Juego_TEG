package com.teg.teg_juego.Repository;

import com.teg.teg_juego.model.entities.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface jugadorRepository extends JpaRepository<Jugador, Integer> {
}
