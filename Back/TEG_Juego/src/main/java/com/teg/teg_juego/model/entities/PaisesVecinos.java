package com.teg.teg_juego.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Paises_Vecinos")
public class PaisesVecinos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais")
    private Pais pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais_vecino")
    private Pais paisVecino;


    public PaisesVecinos() {}

    public PaisesVecinos(int id, Pais pais, Pais paisVecino) {
        this.id = id;
        this.pais = pais;
        this.paisVecino = paisVecino;
    }
}