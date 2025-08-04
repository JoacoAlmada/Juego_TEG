package com.teg.teg_juego.Repository;

import com.teg.teg_juego.model.entities.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface partidaRepository extends JpaRepository<Partida, Integer> {
    List<Partida> findAll();
}
