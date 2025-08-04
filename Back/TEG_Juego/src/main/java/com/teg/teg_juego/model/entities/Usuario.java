package com.teg.teg_juego.model.entities;

import com.teg.teg_juego.model.enums.TipoJugador;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "contrasenia", length = 50)
    private String contrasenia;

    @Column(name = "nivel")
    private Integer nivel;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_tipo_jugador", length = 10)
    private TipoJugador idTipoJugador;


    public Usuario() {}

    public Usuario(Integer id, String nombre, String contrasenia, Integer nivel, TipoJugador idTipoJugador) {
        this.id = id;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.nivel = nivel;
        this.idTipoJugador = idTipoJugador;
    }
}

