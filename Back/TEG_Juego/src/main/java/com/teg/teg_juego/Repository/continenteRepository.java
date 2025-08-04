package com.teg.teg_juego.Repository;

import com.teg.teg_juego.model.entities.Continente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface continenteRepository extends JpaRepository<Continente, Integer> {
}
