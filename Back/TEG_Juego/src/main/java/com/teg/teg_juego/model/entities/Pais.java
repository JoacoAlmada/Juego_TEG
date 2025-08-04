package com.teg.teg_juego.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "Paises")
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pais")
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "continente")
    @JsonIgnore
    private Continente continente;

    @Column(name = "ejercito")
    private Integer ejercito = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Jugador")
    private Jugador jugador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Partida")
    private Partida partida;

    @OneToMany(mappedBy = "pais", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaisesVecinos> vecinos = new ArrayList<>();


    public Pais() {}

    public Pais(Integer id, String nombre, Continente continente, Jugador jugador, Integer ejercito, List<PaisesVecinos> vecinos) {
        this.id = id;
        this.nombre = nombre;
        this.continente = continente;
        this.jugador = jugador;
        this.ejercito = (ejercito != null) ? ejercito : 0;
        this.vecinos = vecinos;
    }
    public List<Pais> getVecinos() {
        return vecinos.stream().map(PaisesVecinos::getPaisVecino).collect(Collectors.toList());
    }
    public void setVecino(Pais pais) {
        PaisesVecinos relacion = new PaisesVecinos();
        relacion.setPais(this);
        relacion.setPaisVecino(pais);

        vecinos.add(relacion);
    }
    public Integer getEjercito() {
        return (ejercito == null) ? 0 : ejercito;
    }

    public void agregarVecino(Pais vecino) {
        PaisesVecinos pv = new PaisesVecinos();
        pv.setPais(this);
        pv.setPaisVecino(vecino);
        this.vecinos.add(pv);
    }
}
