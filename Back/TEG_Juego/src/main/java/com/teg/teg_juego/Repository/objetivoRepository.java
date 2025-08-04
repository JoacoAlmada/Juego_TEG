package com.teg.teg_juego.Repository;

import com.teg.teg_juego.model.entities.Objetivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface objetivoRepository extends JpaRepository<Objetivo, Integer> {
    List<Objetivo> findAll();
}
