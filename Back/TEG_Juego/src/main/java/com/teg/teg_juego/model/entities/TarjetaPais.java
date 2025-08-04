package com.teg.teg_juego.model.entities;

import com.teg.teg_juego.model.enums.Simbolo;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Tarjetas")
public class TarjetaPais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(name = "simbolo", length = 15)
    private Simbolo simbolo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pais")
    private Pais pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Jugador")
    private Jugador jugador;

    public TarjetaPais() {}

    public TarjetaPais(Integer number, Simbolo simbolo, Pais pais) {
        this.number = number;
        this.simbolo = simbolo;
        this.pais = pais;
    }
}