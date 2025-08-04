package com.teg.teg_juego.model.entities;


import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.EstadoPartida;
import com.teg.teg_juego.model.enums.Fase;
import com.teg.teg_juego.model.enums.TipoObjetivo;
import com.teg.teg_juego.model.interfaces.IFase;
import com.teg.teg_juego.model.interfaces.IPartida;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Partidas")
public class Partida implements IPartida, IFase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partida")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_partida", length = 15)
    private EstadoPartida estadoPartida;

    @Column(name = "cantidad_jugadores")
    private Integer cantidadJugadores;

    @Column(name = "ronda")
    private Integer ronda;

    @Column(name = "turno")
    private Integer turno = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "fase", length = 15)
    private Fase fase;

    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Jugador> jugadores = new ArrayList<>();

    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pais> paises = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "partida_objetivos",
            joinColumns = @JoinColumn(name = "partida_id"),
            inverseJoinColumns = @JoinColumn(name = "objetivo_id"))
    private List<Objetivo> objetivos = new ArrayList<>();


    @Transient
    private Jugador ganadorTemporal;

    public Partida() {}

    public Partida(Integer id, EstadoPartida estadoPartida, Integer cantidadJugadores, Integer ronda, Fase fase, List<Pais> paises, List<Objetivo> objetivos) {
        this.id = id;
        this.estadoPartida = estadoPartida;
        this.cantidadJugadores = cantidadJugadores;
        this.ronda = ronda;
        this.fase = fase;
        this.paises = paises;
        this.objetivos = objetivos;
    }


    @Override
    public void iniciarPartida() {
        estadoPartida = EstadoPartida.EN_JUEGO;
    }

    @Override
    public void guardarPartida() {
        estadoPartida = EstadoPartida.GUARDADA;
    }

    @Override
    public Jugador terminarPartida(Jugador jugador) {
        estadoPartida = EstadoPartida.TERMINADA;
        ganadorTemporal = jugador;

        return jugador;
    }

    public boolean estaTerminada() {
        return this.estadoPartida == EstadoPartida.TERMINADA;
    }

    @Override
    public void repartirObjetivos(List<Objetivo> todosLosObjetivos) {
        if (jugadores == null || jugadores.isEmpty()) {
            throw new RuntimeException("No hay jugadores en la partida");
        }

        if (todosLosObjetivos == null || todosLosObjetivos.isEmpty()) {
            throw new RuntimeException("No hay objetivos disponibles");
        }

        List<Objetivo> objetivosDisponibles = new ArrayList<>(todosLosObjetivos);
        Collections.shuffle(objetivosDisponibles);

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            Objetivo objetivo = objetivosDisponibles.get(i);
            while (objetivo.getTipo().equals(TipoObjetivo.ELIMINAR)) {
                Color color = objetivo.obtenerColor(objetivo.getDescripcion());
                boolean valido = jugadores.stream().anyMatch(j -> j.getColor() == color);

                if (valido) {
                    break;
                } else {
                    objetivosDisponibles.remove(i);
                    objetivo = objetivosDisponibles.get(i);
                }
            }

            jugador.setObjetivo(objetivo);
        }
    }

    @Override
    public void pasarTurno() {
        turno = (turno + 1) % jugadores.size();

        if (turno == 0) {
            System.out.println("[Partida] Nueva ronda");
            iniciarRonda();
            repartirEjercitos();
        } else {
            fase = (ronda == 3) ? Fase.ATAQUE : Fase.COLOCACION;

            System.out.println("[Partida] Nuevo turno: " + turno + ", fase: " + fase);
        }
    }

    @Override
    public void repartirPaises(List<Pais> todosLosPaises) {
        if (jugadores == null || jugadores.isEmpty()) {
            throw new RuntimeException("No hay jugadores en la partida");
        }

        if (todosLosPaises == null || todosLosPaises.isEmpty()) {
            throw new RuntimeException("No hay países disponibles");
        }

        for (Jugador jugador : jugadores) {
            jugador.getPaises().clear();
        }

        List<Pais> paisesDisponibles = new ArrayList<>(todosLosPaises);
        Collections.shuffle(paisesDisponibles);

        List<Jugador> jugadoresMezclados = new ArrayList<>(jugadores);
        Collections.shuffle(jugadoresMezclados);

        int cantidadJugadores = jugadores.size();
        int totalPaises = paisesDisponibles.size();

        int paisesPorJugadorBase = totalPaises / cantidadJugadores;
        int sobrantes = totalPaises % cantidadJugadores;

        int indicePais = 0;

        for (int i = 0; i < cantidadJugadores; i++) {
            Jugador jugador = jugadoresMezclados.get(i);
            int cantidadAsignar = paisesPorJugadorBase + (i < sobrantes ? 1 : 0);

            for (int j = 0; j < cantidadAsignar; j++) {
                Pais pais = paisesDisponibles.get(indicePais++);
                pais.setJugador(jugador);
                pais.setEjercito(1);
                jugador.getPaises().add(pais);
            }
        }
    }


    @Override
    public void repartirEjercitos() {
        jugadores.forEach(j -> j.setFichasJ(0));
        if (ronda == 1) {
            jugadores.forEach(j -> j.setFichasJ(5));
        }
        else if (ronda == 2) {
            jugadores.forEach(j -> j.setFichasJ(3));
        } else if (ronda == 3) {
            jugadores.forEach(j -> j.setFichasJ(0));
        } else if (ronda > 3) {
            jugadores.forEach(j -> {
                if (j.getPaises().size() <= 7) {
                    j.setFichasJ(3);
                } else {
                    j.setFichasJ(j.getPaises().size() / 2);
                }
                if(j.tieneContinenteConquistado("AmericaSur")) j.setFichasJ(j.getFichasJ() + 3);;
                if(j.tieneContinenteConquistado("AmericaNorte")) j.setFichasJ(j.getFichasJ() + 5);
                if(j.tieneContinenteConquistado("Africa")) j.setFichasJ(j.getFichasJ() + 3);
                if(j.tieneContinenteConquistado("Europa")) j.setFichasJ(j.getFichasJ() + 5);
                if(j.tieneContinenteConquistado("Oceania")) j.setFichasJ(j.getFichasJ() + 2);
                if(j.tieneContinenteConquistado("Asia")) j.setFichasJ(j.getFichasJ() + 7);
            });
        }
    }

    @Override
    public void pasarFase() {
        if (ronda == null) ronda = 1;

        System.out.println("[Partida] Fase actual: " + fase);
        switch (fase) {
            case COLOCACION -> {
                fase = Fase.ATAQUE;
                System.out.println("[Partida] Avanzando a fase ATAQUE");
            }
            case ATAQUE -> {
                fase = Fase.REAGRUPACION;
                System.out.println("[Partida] Avanzando a fase REAGRUPACION");
            }
            case REAGRUPACION -> {
                System.out.println("[Partida] Avanzando turno");
                pasarTurno();
            }
            default -> throw new IllegalStateException("Fase desconocida: " + fase);
        }
    }

    public Integer getJugadorActualId() {
        if (jugadores != null && turno != null) {
            return jugadores.get(turno).getId();
        }
        return null;
    }
    public String getJugadorActualNombre() {
        if (jugadores != null && !jugadores.isEmpty()) {
            return jugadores.get(turno).getNombreJ();
        }
        return null;
    }

    @Override
    public void iniciarRonda() {
        if (ronda == null) {
            ronda = 1;
        } else {
            ronda++;
        }

        turno = 0;
        repartirEjercitos();

        fase = (ronda == 3) ? Fase.ATAQUE : Fase.COLOCACION;

        System.out.println("[Partida] Iniciando ronda " + ronda + ", turno: " + turno + ", fase inicial: " + fase);
    }

}
